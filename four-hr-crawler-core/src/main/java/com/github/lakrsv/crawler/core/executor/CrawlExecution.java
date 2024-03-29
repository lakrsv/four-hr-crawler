package com.github.lakrsv.crawler.core.executor;

import com.github.lakrsv.crawler.core.dto.CrawlRequestContext;
import com.github.lakrsv.crawler.core.exception.CrawlException;
import com.github.lakrsv.crawler.core.filter.UriFilter;
import com.github.lakrsv.crawler.core.result.ResultHandler;
import com.github.lakrsv.crawler.core.scraper.CrawlScraper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;

import java.net.URI;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CrawlExecution {
    private static final long PROGRESS_POLL_INTERVAL = 1000L;

    private final CrawlRequestContext requestContext;
    private final ResultHandler resultHandler;
    private final CrawlScraper crawlScraper;
    private final ExecutorService executorService;
    // TODO: Does it make sense to track running futures with an atomic integer instead?
    private final Set<Future<?>> runningFutures = ConcurrentHashMap.newKeySet();
    private final Set<URI> visited = ConcurrentHashMap.newKeySet();

    private UriFilter uriFilter;

    public static CrawlExecution create(CrawlRequestContext context, ResultHandler resultHandler, CrawlScraper scraper, ExecutorService executorService) {
        return new CrawlExecution(context, resultHandler, scraper, executorService);
    }

    public void execute() {
        uriFilter = requestContext.configuration().getUrlFilter();

        executorService.submit(() -> {
            resultHandler.onCrawlStarting(requestContext);

            enqueueCrawl(requestContext.request().target());
            waitForCompletion();

            resultHandler.onCrawlFinished(requestContext, null);
        });
    }

    private void waitForCompletion() {
        do {
            try {
                Thread.sleep(PROGRESS_POLL_INTERVAL);
            } catch (InterruptedException e) {
                resultHandler.onCrawlError(requestContext, requestContext.request().target(), new CrawlException("Interrupted", e));
                Thread.currentThread().interrupt();
            }
        } while (!runningFutures.isEmpty());
    }

    private void enqueueCrawl(URI target) {
        var future = CompletableFuture.runAsync(() -> executeWorker(target), executorService);
        runningFutures.add(future);
        future.whenComplete((result, err) -> runningFutures.remove(future));
    }

    private void executeWorker(URI target) {
        if (visited.contains(target)) {
            return;
        }
        visited.add(target);

        var scrapeResult = crawlScraper.scrape(target);
        resultHandler.onCrawlProgress(requestContext, target, scrapeResult);

        extractLinks(scrapeResult)
                .stream()
                .filter(uri -> uriFilter.accept(uri))
                .forEach(this::enqueueCrawl);
    }

    private Set<URI> extractLinks(Document document) {
        var links = document.select("a[href]");
        return links.stream()
                .map(link -> link.attr("abs:href"))
                .map(URI::create)
                .collect(Collectors.toSet());
    }
}
