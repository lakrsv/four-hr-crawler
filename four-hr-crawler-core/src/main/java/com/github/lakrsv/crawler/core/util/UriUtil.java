package com.github.lakrsv.crawler.core.util;

public class UriUtil {
    private UriUtil() {
        // Prevent instantiation
    }

    public static String getDomainName(String host) {
        if (host == null) {
            return null;
        }
        return host.startsWith("www.") ? host.substring(4) : host;
    }
}
