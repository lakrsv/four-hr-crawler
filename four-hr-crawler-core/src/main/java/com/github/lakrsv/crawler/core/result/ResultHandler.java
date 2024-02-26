package com.github.lakrsv.crawler.core.result;

import com.github.lakrsv.crawler.core.dto.CrawlRequestContext;
import com.github.lakrsv.crawler.core.exception.CrawlException;
import org.jsoup.nodes.Document;

import java.net.URI;

public interface ResultHandler {

    void onCrawlStarting(CrawlRequestContext context);

    // TODO: Fix leaky abstraction?
    void onCrawlProgress(CrawlRequestContext context, URI target, Document document);

    void onCrawlFinished(CrawlRequestContext context, CrawlException e);

    void onCrawlError(CrawlRequestContext context, URI target, CrawlException e);
}
