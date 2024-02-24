package com.github.lakrsv.crawler.core.result;

import com.github.lakrsv.crawler.core.dto.CrawlRequest;
import com.github.lakrsv.crawler.core.exception.CrawlException;
import org.jsoup.nodes.Document;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogResultHandler implements ResultHandler {
    private static final Logger LOGGER = Logger.getLogger(LogResultHandler.class.getName());

    @Override
    public void onCrawlStarted(CrawlRequest crawlRequest) {
        LOGGER.log(Level.INFO, "onCrawlStarted()");
    }

    @Override
    public void onCrawlProgress(CrawlRequest crawlRequest, URI target, Document document) {
        LOGGER.log(Level.INFO, "onCrawlProgress() target: " + target); // + " document: " + document.toString()

    }

    @Override
    public void onCrawlFinished(CrawlRequest crawlRequest) {
        LOGGER.log(Level.INFO, "onCrawlFinished()");

    }

    @Override
    public void onCrawlError(CrawlRequest crawlRequest, URI target, CrawlException e) {
        LOGGER.log(Level.INFO, "onCrawlError() target: " + target + " e: " + e);
    }
}
