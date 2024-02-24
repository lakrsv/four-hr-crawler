package com.github.lakrsv.crawler.app.handler;

import com.github.lakrsv.crawler.app.service.CrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CrawlHandler {
    private final CrawlerService crawlerService;

    public Mono<ServerResponse> submitCrawl(ServerRequest request) {
        var url = request.queryParam("url").orElse(null);
        if (url == null) {
            return ServerResponse.badRequest().build();
        }
        var response = crawlerService.submitCrawl(url);
        return ServerResponse.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(response));
    }
}
