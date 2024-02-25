package com.github.lakrsv.crawler.app.domain.response;

import com.github.lakrsv.crawler.app.repository.state.CrawlStatus;

public record GetCrawlStatusResponse(CrawlStatus status) {
}
