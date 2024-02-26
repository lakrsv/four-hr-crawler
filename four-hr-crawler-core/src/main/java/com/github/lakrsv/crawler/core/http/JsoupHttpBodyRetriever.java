package com.github.lakrsv.crawler.core.http;

import com.github.lakrsv.crawler.core.exception.CrawlException;
import org.jsoup.Jsoup;

import java.io.IOException;

public class JsoupHttpBodyRetriever implements HttpBodyRetriever {
    @Override
    public String retrieveBody(String url) {
        try {
            return Jsoup.connect(url).execute().body();
        } catch (IOException e) {
            throw new CrawlException("Failed retrieving body", e);
        }
    }
}
