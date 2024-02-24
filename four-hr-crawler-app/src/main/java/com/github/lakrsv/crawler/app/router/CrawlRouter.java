package com.github.lakrsv.crawler.app.router;

import com.github.lakrsv.crawler.app.handler.CrawlHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class CrawlRouter {

    private static final String API_BASE_PATH = "/crawl/";
    private static final String API_VERSION = "v1";
    private static final String SUBMIT_CRAWL_RESOURCE_PATH = "/submit";

    @Bean
    public RouterFunction<ServerResponse> route(CrawlHandler crawlHandler) {
        return RouterFunctions
                .route()
                .POST(
                        constructRoute(SUBMIT_CRAWL_RESOURCE_PATH),
                        RequestPredicates.queryParam("url", t -> true),
                        crawlHandler::submitCrawl)
                .build();
    }

    private String constructRoute(String resourcePath) {
        return API_BASE_PATH + API_VERSION + resourcePath;
    }
}