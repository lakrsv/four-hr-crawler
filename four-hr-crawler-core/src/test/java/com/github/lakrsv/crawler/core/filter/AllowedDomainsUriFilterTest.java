package com.github.lakrsv.crawler.core.filter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URI;
import java.util.Set;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AllowedDomainsUriFilterTest {
    private final AllowedDomainsUriFilter MULTI_DOMAIN_FILTER = new AllowedDomainsUriFilter(Set.of(Pattern.compile("monzo.com"), Pattern.compile(".*.monzo.com")));
    private final AllowedDomainsUriFilter ONE_SUBDOMAIN_FILTER = new AllowedDomainsUriFilter(Set.of(Pattern.compile("community.monzo.com")));


    @ParameterizedTest
    @ValueSource(strings = {"http://community.monzo.com", "http://monzo.com"})
    public void acceptReturnsTrueWhenRegexMatches(String uri) {
        assertTrue(MULTI_DOMAIN_FILTER.accept(URI.create(uri)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"http://www.facebook.com", "http://community.facebook.com"})
    public void acceptReturnsFalseWhenRegexDoesNotMatch(String uri) {
        assertFalse(MULTI_DOMAIN_FILTER.accept(URI.create(uri)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"http://community.monzo.com/cool-stuff", "http://community.monzo.com", "http://community.monzo.com/wow/cool"})
    public void acceptReturnsTrueWhenRegexMatchesForSpecificSubdomain(String uri) {
        assertTrue(ONE_SUBDOMAIN_FILTER.accept(URI.create(uri)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"http://forum.monzo.com/cool-stuff", "http://forum.monzo.com", "http://forum.monzo.com/wow/cool", "http://monzo.com", "http://www.monzo.com"})
    public void acceptReturnsFalseWhenRegexDoesNotMatchForSpecificSubdomain(String uri) {
        assertFalse(ONE_SUBDOMAIN_FILTER.accept(URI.create(uri)));
    }

    @Test
    public void acceptReturnsFalseWhenUriIsNull() {
        assertFalse(ONE_SUBDOMAIN_FILTER.accept(null));
    }
}
