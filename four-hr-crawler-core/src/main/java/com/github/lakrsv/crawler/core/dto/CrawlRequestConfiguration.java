package com.github.lakrsv.crawler.core.dto;

import com.github.lakrsv.crawler.core.filter.AllowedDomainsUriFilter;
import com.github.lakrsv.crawler.core.filter.ChainedUriFilter;
import com.github.lakrsv.crawler.core.filter.HttpUriFilter;
import com.github.lakrsv.crawler.core.filter.UriFilter;
import com.github.lakrsv.crawler.core.util.UriUtil;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Builder
public class CrawlRequestConfiguration {
    Set<String> allowedDomains;

    public UriFilter getUrlFilter() {
        var filters = new ArrayList<UriFilter>();
        filters.add(new HttpUriFilter());
        if (!allowedDomains.isEmpty()) {
            filters.add(new AllowedDomainsUriFilter(allowedDomains.stream()
                    .map(UriUtil::getDomainName)
                    .map(String::toString)
                    .map(Pattern::compile)
                    .collect(Collectors.toSet())));
        }
        return new ChainedUriFilter(filters);
    }
}