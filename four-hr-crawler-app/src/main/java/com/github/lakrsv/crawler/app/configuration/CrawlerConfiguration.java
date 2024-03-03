package com.github.lakrsv.crawler.app.configuration;

import com.github.lakrsv.crawler.app.http.WebClientHttpBodyRetriever;
import com.github.lakrsv.crawler.core.FourHrCrawler;
import com.github.lakrsv.crawler.core.http.HttpBodyRetriever;
import com.github.lakrsv.crawler.core.scraper.CrawlScraper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.JdkClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.http.HttpClient;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class CrawlerConfiguration {
    @Bean
    public ExecutorService crawlerExecutorService() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .clientConnector(new JdkClientHttpConnector(HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build()))
// TODO: Tweak the knobs to get reactor client to work well
//                .clientConnector(new ReactorClientHttpConnector(HttpClient
//                        .create(ConnectionProvider.builder("custom")
//                                .maxConnections(50)
//                                .maxIdleTime(Duration.ofSeconds(20))
//                                .maxLifeTime(Duration.ofSeconds(60))
//                                .pendingAcquireTimeout(Duration.ofSeconds(60))
//                                .evictInBackground(Duration.ofSeconds(120))
//                                .build())
//                        .followRedirect(true)))
                .build();
    }

    @Bean
    public HttpBodyRetriever httpBodyRetriever(WebClient webClient) {
        return new WebClientHttpBodyRetriever(webClient);
    }

//    @Bean
//    public HttpBodyRetriever httpBodyRetriever(){
//        return new JsoupHttpBodyRetriever();
//    }

    @Bean
    public CrawlScraper crawlScraper(HttpBodyRetriever httpBodyRetriever) {
        return new CrawlScraper(httpBodyRetriever);
    }

    @Bean
    public FourHrCrawler crawler(ExecutorService crawlerExecutorService, CrawlScraper crawlScraper) {
        return new FourHrCrawler(crawlerExecutorService, crawlScraper);
    }
}
