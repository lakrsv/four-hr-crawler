package com.github.lakrsv.crawler.core.filter;

import java.net.URI;

public class HttpUriFilter implements UriFilter {
    @Override
    public boolean accept(URI uri) {
        if (uri == null) {
            return false;
        }
        return uri.getScheme().equalsIgnoreCase("http") || uri.getScheme().equalsIgnoreCase("https");
    }
}
