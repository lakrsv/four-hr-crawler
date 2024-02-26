package com.github.lakrsv.crawler.app.repository.state;

import com.github.lakrsv.crawler.core.dto.CrawlRequestContext;

import java.util.Optional;

public interface CrawlStateRepository {
    boolean tryStartCrawl(CrawlRequestContext context);

    boolean finishCrawl(CrawlRequestContext context, boolean error);

    Optional<CrawlStatus> getCrawlStatus(String crawlId);
}
