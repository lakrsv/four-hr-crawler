package com.github.lakrsv.crawler.core.scraper;

import com.github.lakrsv.crawler.core.exception.CrawlException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;

public class CrawlScraper {
    public Document scrape(URI uri) {
        if (uri == null) {
            throw new CrawlException("uri can not be null");
        }
        Document document;
        try {
            // TODO: Don't use this -- Creates connection per request
            document = Jsoup.connect(uri.toString()).get();
        } catch (IOException e) {
            throw new CrawlException("Failed scraping page", e);
        }
        return document;
    }
}
