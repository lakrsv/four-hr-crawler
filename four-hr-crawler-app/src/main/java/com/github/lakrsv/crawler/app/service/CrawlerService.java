package com.github.lakrsv.crawler.app.service;

import com.github.lakrsv.crawler.app.dto.SubmitCrawlResponse;
import com.github.lakrsv.crawler.core.FourHrCrawler;
import com.github.lakrsv.crawler.core.dto.CrawlRequest;
import com.github.lakrsv.crawler.core.dto.CrawlRequestConfiguration;
import com.github.lakrsv.crawler.core.result.ResultHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CrawlerService {
    private final FourHrCrawler crawler;
    private final ResultHandler resultHandler;

    public SubmitCrawlResponse submitCrawl(String url) {
        var uri = URI.create(url);
        crawler.startCrawl(new CrawlRequest(uri, CrawlRequestConfiguration.builder()
                .allowedDomains(Set.of(uri.getHost()))
                .build()), resultHandler);
        // TODO: We'd really like to decouple this, and have a id to use for Dynamo as well. So we can reuse cached responses. Running out of time.
        return new SubmitCrawlResponse(Base64.getEncoder().encodeToString(url.getBytes(StandardCharsets.UTF_8)));
    }

    // public PollCrawlStatusResponse pollCrawlStatus(String crawlId){...}
    // public CrawlResultResponse getCrawlResult(String crawlId){...}
}
