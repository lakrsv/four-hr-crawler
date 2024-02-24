package com.github.lakrsv.crawler.core.filter;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChainedUriFilterTest {
    private final ChainedUriFilter chainedUriFilter = new ChainedUriFilter(List.of(new HttpUriFilter(), new AllowedDomainsUriFilter(Set.of(Pattern.compile("rapid7.com")))));

    @Test
    public void acceptReturnsTrueWhenNoFiltersAreApplied() {
        assertTrue(new ChainedUriFilter(List.of()).accept(URI.create("http://www.rapid7.com")));
    }

    @Test
    public void acceptReturnsFalseWhenUriIsNull() {
        assertFalse(new ChainedUriFilter(List.of()).accept(null));
    }

    @Test
    public void acceptReturnsFalseWhenSchemeDoesNotMatch() {
        assertFalse(chainedUriFilter.accept(URI.create("ftp://rapid7.com")));
    }

    @Test
    public void acceptReturnsFalseWhenDomainDoesNotMatch() {
        assertFalse(chainedUriFilter.accept(URI.create("http://google.com")));
    }

    @Test
    public void acceptReturnsTrueWhenSchemeAndDomainMatch() {
        assertTrue(chainedUriFilter.accept(URI.create("http://rapid7.com")));

    }
}
