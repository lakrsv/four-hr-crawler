package com.github.lakrsv.crawler.core.filter;

import java.net.URI;

public interface UriFilter {
    boolean accept(URI uri);
}
