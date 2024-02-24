package com.github.lakrsv.crawler.core.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UriUtilTest {
    @Test
    public void getDomainNameWithNullHostReturnsNull() {
        assertNull(UriUtil.getDomainName(null));
    }

    @Test
    public void getDomainNameWithWWWReturnsExpectedDomainName() {
        assertEquals("google.com", UriUtil.getDomainName("www.google.com"));

    }

    @Test
    public void getDomainNameWithValidHostnameReturnsExpectedDomainName() {
        assertEquals("google.com", UriUtil.getDomainName("google.com"));
    }
}
