package com.github.lakrsv.crawler.app.domain.request;

import java.util.Set;

public record SubmitCrawlRequest(String url, Set<String> allowedDomains) {
}
