package com.github.lakrsv.crawler.app.http;

import com.github.lakrsv.crawler.core.http.HttpBodyRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class WebClientHttpBodyRetriever implements HttpBodyRetriever {
    private final WebClient webClient;
    @Override
    public String retrieveBody(String url) {
        return webClient.get().uri(url).retrieve().bodyToMono(String.class).block();
    }
}
