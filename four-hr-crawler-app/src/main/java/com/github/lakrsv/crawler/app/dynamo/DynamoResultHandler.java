package com.github.lakrsv.crawler.app.dynamo;

import com.github.lakrsv.crawler.core.dto.CrawlRequest;
import com.github.lakrsv.crawler.core.exception.CrawlException;
import com.github.lakrsv.crawler.core.result.ResultHandler;
import org.jsoup.nodes.Document;

import java.net.URI;

// TODO: Implement results into DynamoDB
public class DynamoResultHandler implements ResultHandler {
    @Override
    public void onCrawlStarted(CrawlRequest crawlRequest) {

    }

    @Override
    public void onCrawlProgress(CrawlRequest crawlRequest, URI target, Document document) {

    }

    @Override
    public void onCrawlFinished(CrawlRequest crawlRequest) {

    }

    @Override
    public void onCrawlError(CrawlRequest crawlRequest, URI target, CrawlException e) {

    }
}
