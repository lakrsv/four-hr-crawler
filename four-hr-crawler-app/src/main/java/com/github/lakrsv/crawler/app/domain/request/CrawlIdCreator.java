package com.github.lakrsv.crawler.app.domain.request;

import com.github.lakrsv.crawler.core.dto.CrawlRequest;
import org.apache.commons.codec.digest.MurmurHash3;

import java.nio.charset.StandardCharsets;

public class CrawlIdCreator {
    public String createCrawlId(CrawlRequest crawlRequest) {
        var hash = new MurmurHash3.IncrementalHash32x86();

        var uri = crawlRequest.target().toString();
        hash.add(uri.getBytes(StandardCharsets.UTF_8), 0, uri.length());

        return String.valueOf(hash.end());
    }
}
