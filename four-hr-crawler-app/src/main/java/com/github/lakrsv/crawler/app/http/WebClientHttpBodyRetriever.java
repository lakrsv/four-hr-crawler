package com.github.lakrsv.crawler.app.http;

import com.github.lakrsv.crawler.core.http.HttpBodyRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class WebClientHttpBodyRetriever implements HttpBodyRetriever {
    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36";

    private final WebClient webClient;

    @Override
    public String retrieveBody(String url) {
        return webClient.get()
                .uri(url)
                .header(USER_AGENT_HEADER, USER_AGENT)
                .retrieve().bodyToMono(String.class).block();
    }
}
