package com.github.lakrsv.crawler.core.exception;

public class CrawlException extends RuntimeException {
    public CrawlException(String message) {
        super(message);
    }

    public CrawlException(String message, Throwable inner) {
        super(message, inner);
    }
}
