package com.github.lakrsv.crawler.app.configuration;

import com.github.lakrsv.crawler.app.http.WebClientHttpBodyRetriever;
import com.github.lakrsv.crawler.core.FourHrCrawler;
import com.github.lakrsv.crawler.core.http.HttpBodyRetriever;
import com.github.lakrsv.crawler.core.http.JsoupHttpBodyRetriever;
import com.github.lakrsv.crawler.core.scraper.CrawlScraper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class CrawlerConfiguration {
    @Bean
    public ExecutorService crawlerExecutorService() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient
                        .create()
                        .followRedirect(true)))
                .build();
    }
    @Bean
    public HttpBodyRetriever httpBodyRetriever(WebClient webClient) {
        return new WebClientHttpBodyRetriever(webClient);
    }

    @Bean
    public CrawlScraper crawlScraper(HttpBodyRetriever httpBodyRetriever) {
        return new CrawlScraper(httpBodyRetriever);
    }

    @Bean
    public FourHrCrawler crawler(ExecutorService crawlerExecutorService, CrawlScraper crawlScraper) {
        return new FourHrCrawler(crawlerExecutorService, crawlScraper);
    }
}
