package com.github.lakrsv.crawler.core.filter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpUriFilterTest {
    private final HttpUriFilter httpUriFilter = new HttpUriFilter();

    @ParameterizedTest
    @ValueSource(strings = {"ftp://localhost:3928", "admin:/etc/default/grub", "app://com.foo.bar/index.html", "jdbc:mysql://host:port/database", "psns://browse?product=⟨ContentID⟩"})
    public void acceptReturnsFalseForInvalidSchemes(String uri) {
        assertFalse(httpUriFilter.accept(URI.create(uri)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"http://localhost:8080", "https://localhost:8081/foo/bar"})
    public void acceptReturnsTrueForValidSchemes(String uri) {
        assertTrue(httpUriFilter.accept(URI.create(uri)));
    }

    @Test
    public void acceptReturnsFalseForNull() {
        assertFalse(httpUriFilter.accept(null));
    }
}
