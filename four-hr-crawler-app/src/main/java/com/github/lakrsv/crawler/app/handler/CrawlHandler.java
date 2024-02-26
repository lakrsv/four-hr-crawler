package com.github.lakrsv.crawler.app.handler;

import com.github.lakrsv.crawler.app.domain.request.SubmitCrawlRequest;
import com.github.lakrsv.crawler.app.domain.response.SubmitCrawlResponse;
import com.github.lakrsv.crawler.app.service.CrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CrawlHandler {
    private final CrawlerService crawlerService;

    public Mono<ServerResponse> submitCrawl(ServerRequest request) {
        return request.bodyToMono(SubmitCrawlRequest.class)
                .map(req -> crawlerService.submitCrawl(req.url()))
                .flatMap(response -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(response)));
    }

    public Mono<ServerResponse> getCrawlStatus(ServerRequest request) {
        var crawlId = request.pathVariable("crawlId");

        var response = crawlerService.getCrawlStatus(crawlId);
        if(response.status() == null){
            return ServerResponse.notFound().build();
        }

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(response));
    }

    // TODO: Paginate crawl results.. see router todo
//    public Mono<ServerResponse> getCrawlResult(ServerRequest request) {
//        var crawlId = request.pathVariable("crawlId");
//
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromPublisher(Flux.fromIterable(List.of("hello", "world")), String.class));
//
//    }
}
