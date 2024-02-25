package com.github.lakrsv.crawler.core;

import com.github.lakrsv.crawler.core.dto.CrawlRequest;
import com.github.lakrsv.crawler.core.dto.CrawlRequestConfiguration;
import com.github.lakrsv.crawler.core.dto.CrawlRequestContext;
import com.github.lakrsv.crawler.core.result.LogResultHandler;
import com.github.lakrsv.crawler.core.scraper.CrawlScraper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Set;
import java.util.concurrent.Executors;

public class FourHrCrawlerTest {
    @Test
    @Disabled
    public void crawlSimpleTest() throws InterruptedException {
        var scraper = new FourHrCrawler(Executors.newVirtualThreadPerTaskExecutor(), new CrawlScraper());
        var request = new CrawlRequest(URI.create("http://www.monzo.com"));
        var context = new CrawlRequestContext("crawlId", request, CrawlRequestConfiguration.builder()
                .allowedDomains(Set.of(request.target().getHost()))
                .build());
        scraper.startCrawl(context, new LogResultHandler());

        // TODO: Use awaitility
        Thread.sleep(20000);
    }
}
