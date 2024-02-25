package com.github.lakrsv.crawler.app.result;

import com.github.lakrsv.crawler.core.dto.CrawlRequestContext;
import com.github.lakrsv.crawler.core.exception.CrawlException;
import com.github.lakrsv.crawler.core.result.ResultHandler;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;

import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class LoggingResultHandler implements ResultHandler {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void onCrawlStarting(CrawlRequestContext context) {
        log.info("Crawl started with target {}", context.request().target());
        counter.set(0);
    }

    @Override
    public void onCrawlProgress(CrawlRequestContext context, URI target, Document document) {
        var links = document.select("a[href]").stream()
                .map(link -> link.attr("abs:href"))
                .distinct()
                .collect(Collectors.joining(", "));
        log.info("Crawl Progress ({}/?) -- Parent: {}, Children: {}", counter.getAndIncrement(), target, links);
    }

    @Override
    public void onCrawlFinished(CrawlRequestContext context, CrawlException e) {
        log.info("Crawl finished!");
        counter.set(0);
    }

    @Override
    public void onCrawlError(CrawlRequestContext context, URI target, CrawlException e) {
        log.error("Got error during crawl", e);
    }
}
