package com.github.lakrsv.crawler.app.router;

import com.github.lakrsv.crawler.app.handler.CrawlHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.join;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class CrawlRouter {

    private static final String API_PATH_DELIMITER = "/";
    private static final String API_BASE_PATH = "crawl";
    private static final String GET_CRAWL_STATUS_RESOURCE_PATH = "{crawlId}/status";
    // TODO: Implement pagination of results: https://www.baeldung.com/spring-data-webflux-pagination
    private static final String GET_CRAWL_RESULT_RESOURCE_PATH = "/{crawlId}/....";

    @Bean
    public RouterFunction<ServerResponse> route(CrawlHandler crawlHandler) {
        return RouterFunctions
                .route()
                .POST(
                        constructRoute(API_BASE_PATH),
                        accept(MediaType.APPLICATION_JSON),
                        crawlHandler::submitCrawl)
                .GET(
                        constructRoute(API_BASE_PATH, GET_CRAWL_STATUS_RESOURCE_PATH),
                        accept(MediaType.APPLICATION_JSON),
                        crawlHandler::getCrawlStatus)
                .build();
    }

    private String constructRoute(String... resourcePath) {
        return API_PATH_DELIMITER + join("/", resourcePath);
    }

    // TODO: This simply returns 404 - NOT FOUND when URL is invalid. How to implement proper validation with RouterFunction?
    // TODO: Is using @Controller better?
    private boolean validateUrl(String url) {
        try {
            var uri = new URI(url);
            return "http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme());
        } catch (URISyntaxException e) {
            return false;
        }
    }
}