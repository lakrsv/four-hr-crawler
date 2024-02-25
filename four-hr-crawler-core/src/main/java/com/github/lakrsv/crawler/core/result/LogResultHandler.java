package com.github.lakrsv.crawler.core.result;

import com.github.lakrsv.crawler.core.dto.CrawlRequestContext;
import com.github.lakrsv.crawler.core.exception.CrawlException;
import org.jsoup.nodes.Document;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogResultHandler implements ResultHandler {
    private static final Logger LOGGER = Logger.getLogger(LogResultHandler.class.getName());

    @Override
    public void onCrawlStarting(CrawlRequestContext context) {
        LOGGER.log(Level.INFO, "onCrawlStarted()");
    }

    @Override
    public void onCrawlProgress(CrawlRequestContext context, URI target, Document document) {
        LOGGER.log(Level.INFO, "onCrawlProgress() target: " + target); // + " document: " + document.toString()

    }

    @Override
    public void onCrawlFinished(CrawlRequestContext context, CrawlException e) {
        LOGGER.log(Level.INFO, "onCrawlFinished()");

    }

    @Override
    public void onCrawlError(CrawlRequestContext context, URI target, CrawlException e) {
        LOGGER.log(Level.INFO, "onCrawlError() target: " + target + " e: " + e);
    }
}
