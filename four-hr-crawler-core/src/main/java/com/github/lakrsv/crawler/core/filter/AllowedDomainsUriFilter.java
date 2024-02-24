package com.github.lakrsv.crawler.core.filter;

import com.github.lakrsv.crawler.core.util.UriUtil;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.Set;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class AllowedDomainsUriFilter implements UriFilter {
    private final Set<Pattern> allowedDomains;

    @Override
    public boolean accept(URI uri) {
        if (uri == null) {
            return false;
        }
        return allowedDomains.stream()
                .anyMatch(pattern -> pattern.matcher(UriUtil.getDomainName(uri.getHost())).matches());
    }
}
