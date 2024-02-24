package com.github.lakrsv.crawler.core;

import com.github.lakrsv.crawler.core.dto.CrawlRequest;
import com.github.lakrsv.crawler.core.dto.CrawlRequestConfiguration;
import com.github.lakrsv.crawler.core.result.LogResultHandler;
import com.github.lakrsv.crawler.core.scraper.CrawlScraper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Set;
import java.util.concurrent.Executors;

public class FourHrCrawlerTest {
    @Test
    @Disabled
    public void crawlSimpleTest() throws MalformedURLException, InterruptedException {
        var scraper = new FourHrCrawler(Executors.newVirtualThreadPerTaskExecutor(), new CrawlScraper());
        var uri = URI.create("http://www.monzo.com/");
        scraper.startCrawl(new CrawlRequest(uri, CrawlRequestConfiguration.builder()
                .allowedDomains(Set.of(uri.getHost()))
                .build()), new LogResultHandler());

        // TODO: Use awaitility
        Thread.sleep(20000);
    }
}
