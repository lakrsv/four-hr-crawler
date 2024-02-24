package com.github.lakrsv.crawler.core.filter;

import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
public class ChainedUriFilter implements UriFilter {
    private final List<UriFilter> filters;

    @Override
    public boolean accept(URI uri) {
        if (uri == null) {
            return false;
        }
        for (var filter : filters) {
            if (!filter.accept(uri)) {
                return false;
            }
        }
        return true;
    }
}
