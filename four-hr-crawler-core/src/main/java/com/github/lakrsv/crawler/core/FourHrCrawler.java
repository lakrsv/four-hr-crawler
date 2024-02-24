package com.github.lakrsv.crawler.core;

import com.github.lakrsv.crawler.core.dto.CrawlRequest;
import com.github.lakrsv.crawler.core.executor.CrawlExecutor;
import com.github.lakrsv.crawler.core.result.ResultHandler;
import com.github.lakrsv.crawler.core.scraper.CrawlScraper;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
public class FourHrCrawler {
    private final ExecutorService executorService;
    private final CrawlScraper crawlScraper;

    public void startCrawl(CrawlRequest request, ResultHandler resultHandler) {
        new CrawlExecutor(request, crawlScraper, resultHandler, executorService).execute();
    }
}
