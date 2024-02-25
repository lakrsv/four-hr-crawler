package com.github.lakrsv.crawler.app.service;

import com.github.lakrsv.crawler.app.domain.request.CrawlIdCreator;
import com.github.lakrsv.crawler.app.domain.response.GetCrawlStatusResponse;
import com.github.lakrsv.crawler.app.domain.response.SubmitCrawlResponse;
import com.github.lakrsv.crawler.app.repository.state.CrawlStateRepository;
import com.github.lakrsv.crawler.core.FourHrCrawler;
import com.github.lakrsv.crawler.core.dto.CrawlRequest;
import com.github.lakrsv.crawler.core.dto.CrawlRequestConfiguration;
import com.github.lakrsv.crawler.core.dto.CrawlRequestContext;
import com.github.lakrsv.crawler.core.exception.CrawlException;
import com.github.lakrsv.crawler.core.result.ResultHandler;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CrawlerService {
    private final FourHrCrawler crawler;
    private final CrawlIdCreator crawlIdCreator;
    private final CrawlStateRepository crawlStateRepository;
    private final ResultHandler resultHandler;

    public SubmitCrawlResponse submitCrawl(String url) {
        // TODO: URI validation here?
        var request = new CrawlRequest(URI.create(url));
        var requestContext = new CrawlRequestContext(crawlIdCreator.createCrawlId(request), request, CrawlRequestConfiguration.builder()
                .allowedDomains(Set.of(request.target().getHost()))
                .build());
        if (crawlStateRepository.tryStartCrawl(requestContext)) {
            crawler.startCrawl(requestContext, new CrawlResultHandler(resultHandler, crawlStateRepository));
        }
        return new SubmitCrawlResponse(requestContext.crawlId());
    }

    public GetCrawlStatusResponse getCrawlStatus(String crawlId){
        return new GetCrawlStatusResponse(crawlStateRepository.getCrawlStatus(crawlId).orElse(null));
    }
    public void getCrawlResult(String crawlId){

    }

    @RequiredArgsConstructor
    private static class CrawlResultHandler implements ResultHandler {

        private final ResultHandler internalResultHandler;
        private final CrawlStateRepository crawlStateRepository;

        @Override
        public void onCrawlStarting(CrawlRequestContext context) {
            internalResultHandler.onCrawlStarting(context);
        }

        @Override
        public void onCrawlProgress(CrawlRequestContext context, URI target, Document document) {
            internalResultHandler.onCrawlProgress(context, target, document);
        }

        @Override
        public void onCrawlFinished(CrawlRequestContext context, CrawlException e) {
            crawlStateRepository.finishCrawl(context, e != null);
            internalResultHandler.onCrawlFinished(context, e);
        }

        @Override
        public void onCrawlError(CrawlRequestContext context, URI target, CrawlException e) {
            internalResultHandler.onCrawlError(context, target, e);
        }
    }
}
