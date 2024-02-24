package com.github.lakrsv.crawler.core.result;

import com.github.lakrsv.crawler.core.dto.CrawlRequest;
import com.github.lakrsv.crawler.core.exception.CrawlException;
import org.jsoup.nodes.Document;

import java.net.URI;

public interface ResultHandler {
    void onCrawlStarted(CrawlRequest crawlRequest);

    void onCrawlProgress(CrawlRequest crawlRequest, URI target, Document document);

    void onCrawlFinished(CrawlRequest crawlRequest);

    void onCrawlError(CrawlRequest crawlRequest, URI target, CrawlException e);
}
