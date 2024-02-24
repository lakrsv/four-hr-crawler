package com.github.lakrsv.crawler.core.executor;

import com.github.lakrsv.crawler.core.dto.CrawlRequest;
import com.github.lakrsv.crawler.core.dto.CrawlRequestConfiguration;
import com.github.lakrsv.crawler.core.result.ResultHandler;
import com.github.lakrsv.crawler.core.scraper.CrawlScraper;
import org.awaitility.Awaitility;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CrawlExecutorIT {
    @Mock
    private CrawlScraper crawlScraper;
    @Mock
    private Document document;
    @Spy
    private ResultHandler resultHandler;


    @Test
    public void executeInvokesResultHandlerInCorrectOrder() {
        var request = new CrawlRequest(URI.create("http://www.rapid7.com"), CrawlRequestConfiguration.builder()
                .allowedDomains(Set.of("rapid7.com"))
                .build());
        var crawlExecutor = new CrawlExecutor(request, crawlScraper, resultHandler, Executors.newVirtualThreadPerTaskExecutor());
        when(crawlScraper.scrape(any())).thenReturn(document);
        when(document.select(any(String.class))).thenReturn(new Elements());

        crawlExecutor.execute();

        Awaitility.await()
                .atMost(Duration.ofSeconds(3))
                .untilAsserted(() -> verify(resultHandler).onCrawlFinished(any()));

        var inOrder = Mockito.inOrder(resultHandler);
        inOrder.verify(resultHandler).onCrawlStarted(any());
        inOrder.verify(resultHandler).onCrawlProgress(any(), any(), any());
        inOrder.verify(resultHandler).onCrawlFinished(any());
    }

    @Test
    public void executeVisitsAllEnqueuedUrls() {
        var request = new CrawlRequest(URI.create("http://www.rapid7.com"), CrawlRequestConfiguration.builder()
                .allowedDomains(Set.of("rapid7.com"))
                .build());
        var crawlExecutor = new CrawlExecutor(request, crawlScraper, resultHandler, Executors.newVirtualThreadPerTaskExecutor());
        when(crawlScraper.scrape(any())).thenReturn(document);

        var firstBatch = createMockLinkBatch(10, "http://www.rapid7.com/");
        var secondBatch = createMockLinkBatch(10, "http://www.rapid7.com/next/");
        var thirdBatch = createMockLinkBatch(5, "http://www.rapid7.com/bla/");

        when(document.select(any(String.class))).thenReturn(new Elements(firstBatch), new Elements(secondBatch), new Elements(thirdBatch));

        crawlExecutor.execute();

        Awaitility.await()
                .atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> verify(resultHandler).onCrawlFinished(any()));

        verify(resultHandler, times(26)).onCrawlProgress(any(), any(), any());
    }

    @Test
    public void executeVisitsAllEnqueuedUrlsButNotIncludingInvalidOnes() {
        var request = new CrawlRequest(URI.create("http://www.rapid7.com"), CrawlRequestConfiguration.builder()
                .allowedDomains(Set.of("rapid7.com"))
                .build());
        var crawlExecutor = new CrawlExecutor(request, crawlScraper, resultHandler, Executors.newVirtualThreadPerTaskExecutor());
        when(crawlScraper.scrape(any())).thenReturn(document);

        var firstBatch = createMockLinkBatch(10, "http://www.rapid7.com/");
        var secondBatch = createMockLinkBatch(10, "http://www.rapid7.com/next/");
        var thirdBatch = createMockLinkBatch(5, "http://www.monzo.com/bla/");

        when(document.select(any(String.class))).thenReturn(new Elements(firstBatch), new Elements(secondBatch), new Elements(thirdBatch));

        crawlExecutor.execute();

        Awaitility.await()
                .atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> verify(resultHandler).onCrawlFinished(any()));

        verify(resultHandler, times(21)).onCrawlProgress(any(), any(), any());
    }

    private List<Element> createMockLinkBatch(int count, String prefix) {
        return IntStream.range(0, count)
                .mapToObj(i -> {
                    var element = Mockito.mock(Element.class);
                    when(element.attr(any())).thenReturn(prefix + i);
                    return element;
                })
                .toList();
    }

}
