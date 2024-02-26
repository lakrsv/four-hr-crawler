package com.github.lakrsv.crawler.core.scraper;

import com.github.lakrsv.crawler.core.exception.CrawlException;
import com.github.lakrsv.crawler.core.http.JsoupHttpBodyRetriever;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// Sites from https://webscraper.io/test-sites
public class CrawlScraperIT {
    private static final Set<String> EXPECTED_LINKS = Set.of(
            "https://webscraper.io/pricing",
            "mailto:info@webscraper.io",
            "https://webscraper.io/test-sites/e-commerce/static/phones",
            "https://webscraper.io/test-sites/e-commerce/static/computers",
            "https://webscraper.io/documentation",
            "https://webscraper.io/",
            "https://webscraper.io/cloud-scraper",
            "https://webscraper.io/test-sites",
            "https://webscraper.io/privacy-policy",
            "https://webscraper.io/tutorials",
            "https://webscraper.io/screenshots",
            "https://webscraper.io/about-us",
            "https://webscraper.io/jobs",
            "https://webscraper.io/test-sites/e-commerce/static",
            "https://webscraper.io/downloads/Web_Scraper_Media_Kit.zip",
            "https://cloud.webscraper.io/",
            "https://webscraper.io/how-to-videos",
            "https://www.facebook.com/webscraperio/",
            "https://status.webscraper.io/",
            "https://webscraper.io/contact",
            "https://webscraper.io/extension-privacy-policy",
            "https://webscraper.io/test-sites/e-commerce/static#",
            "https://forum.webscraper.io/",
            "https://webscraper.io/blog"
    );
    private final CrawlScraper crawlScraper = new CrawlScraper(new JsoupHttpBodyRetriever());

    @Test
    public void scrapeWithKnownTestSiteReturnsExpectedLinks() {
        var scrapeResult = crawlScraper.scrape(URI.create("https://webscraper.io/test-sites/e-commerce/static"));
        var actual = scrapeResult.select("a[href]").stream()
                .map(element -> element.attr("abs:href"))
                .collect(Collectors.toSet());
        assertThat(actual).containsAll(EXPECTED_LINKS);
    }

    @Test
    public void scrapeWithInvalidUriSchemeThrowsCrawlException() {
        assertThatThrownBy(() -> crawlScraper.scrape(URI.create("ftp://localhost:45040")))
                .isInstanceOf(CrawlException.class);
    }

    @Test
    public void scrapeWithNullUriThrowsCrawlException() {
        assertThatThrownBy(() -> crawlScraper.scrape(null))
                .isInstanceOf(CrawlException.class);
    }

    @Test
    public void scrapeWithUriThatDoesNotExistThrowsCrawlException() {
        assertThatThrownBy(() -> crawlScraper.scrape(URI.create("http://thisdoesnotexiaskdqwejasdkadladswo.com")))
                .isInstanceOf(CrawlException.class);
    }
}
