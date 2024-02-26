package com.github.lakrsv.crawler.core.dto;

public record CrawlRequestContext(
        String crawlId,
    CrawlRequest request,
    CrawlRequestConfiguration configuration) {
}
