package com.github.lakrsv.crawler.app.dynamo;

import com.github.lakrsv.crawler.core.dto.CrawlRequest;
import com.github.lakrsv.crawler.core.exception.CrawlException;
import com.github.lakrsv.crawler.core.result.ResultHandler;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;

import java.net.URI;
import java.util.stream.Collectors;

@Slf4j
public class LoggingResultHandler implements ResultHandler {
    @Override
    public void onCrawlStarted(CrawlRequest crawlRequest) {
        log.info("Crawl started with target {}", crawlRequest.target());
    }

    @Override
    public void onCrawlProgress(CrawlRequest crawlRequest, URI target, Document document) {
        var links = document.select("a[href]").stream()
                .map(link -> link.attr("abs:href"))
                .collect(Collectors.joining(", "));
        log.info("Crawl Progress -- Parent: {}, Children: {}", target, links);
    }

    @Override
    public void onCrawlFinished(CrawlRequest crawlRequest) {
        log.info("Crawl finished!");
    }

    @Override
    public void onCrawlError(CrawlRequest crawlRequest, URI target, CrawlException e) {
        log.error("Got error during crawl", e);
    }
}
