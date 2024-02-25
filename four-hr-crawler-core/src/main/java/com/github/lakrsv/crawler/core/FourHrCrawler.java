package com.github.lakrsv.crawler.core;

import com.github.lakrsv.crawler.core.dto.CrawlRequestContext;
import com.github.lakrsv.crawler.core.executor.CrawlExecution;
import com.github.lakrsv.crawler.core.result.ResultHandler;
import com.github.lakrsv.crawler.core.scraper.CrawlScraper;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
public class FourHrCrawler {
    private final ExecutorService executorService;
    private final CrawlScraper crawlScraper;

//    public CrawlExecution createCrawl(CrawlRequestContext context, ResultHandler resultHandler) {
//        return CrawlExecution.create(context, resultHandler, crawlScraper, executorService);
//    }

    public CrawlExecution startCrawl(CrawlRequestContext context, ResultHandler resultHandler) {
        var crawlExecution = CrawlExecution.create(context, resultHandler, crawlScraper, executorService);
        crawlExecution.execute();
        return crawlExecution;
    }
}
