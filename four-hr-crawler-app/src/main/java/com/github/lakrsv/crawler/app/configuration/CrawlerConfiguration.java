package com.github.lakrsv.crawler.app.configuration;

import com.github.lakrsv.crawler.core.FourHrCrawler;
import com.github.lakrsv.crawler.core.http.JsoupHttpBodyRetriever;
import com.github.lakrsv.crawler.core.scraper.CrawlScraper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class CrawlerConfiguration {
    @Bean
    public ExecutorService crawlerExecutorService() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean
    public CrawlScraper crawlScraper() {
        return new CrawlScraper(new JsoupHttpBodyRetriever());
    }

    @Bean
    public FourHrCrawler crawler(ExecutorService crawlerExecutorService, CrawlScraper crawlScraper) {
        return new FourHrCrawler(crawlerExecutorService, crawlScraper);
    }
}
