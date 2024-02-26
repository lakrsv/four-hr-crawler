package com.github.lakrsv.crawler.core.scraper;

import com.github.lakrsv.crawler.core.exception.CrawlException;
import com.github.lakrsv.crawler.core.http.HttpBodyRetriever;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URI;

@RequiredArgsConstructor
public class CrawlScraper {
    private final HttpBodyRetriever httpBodyRetriever;

    public Document scrape(URI uri) {
        if (uri == null) {
            throw new CrawlException("uri can not be null");
        }
        var body = httpBodyRetriever.retrieveBody(uri.toString());
        return Jsoup.parse(body, uri.toString());
    }
}
