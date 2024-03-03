package com.github.lakrsv.crawler.app.service;

import com.github.lakrsv.crawler.app.domain.request.CrawlIdCreator;
import com.github.lakrsv.crawler.app.domain.response.GetCrawlStatusResponse;
import com.github.lakrsv.crawler.app.domain.response.SubmitCrawlResponse;
import com.github.lakrsv.crawler.app.repository.result.DomainEntity;
import com.github.lakrsv.crawler.app.repository.result.DomainRepository;
import com.github.lakrsv.crawler.app.repository.state.CrawlStateRepository;
import com.github.lakrsv.crawler.core.FourHrCrawler;
import com.github.lakrsv.crawler.core.dto.CrawlRequest;
import com.github.lakrsv.crawler.core.dto.CrawlRequestConfiguration;
import com.github.lakrsv.crawler.core.dto.CrawlRequestContext;
import com.github.lakrsv.crawler.core.exception.CrawlException;
import com.github.lakrsv.crawler.core.result.ResultHandler;
import com.github.lakrsv.crawler.core.util.UriUtil;
import com.google.common.net.InternetDomainName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlerService {
    private final FourHrCrawler crawler;
    private final CrawlIdCreator crawlIdCreator;
    private final CrawlStateRepository crawlStateRepository;
    private final DomainRepository domainRepository;
    private final Optional<ResultHandler> resultHandler;

    public SubmitCrawlResponse submitCrawl(String url, Set<String> allowedDomains) {
        // TODO: URI validation here?
        var request = new CrawlRequest(URI.create(url));
        var requestContext = new CrawlRequestContext(crawlIdCreator.createCrawlId(request), request, CrawlRequestConfiguration.builder()
                .allowedDomains(allowedDomains.isEmpty() ? Set.of(request.target().getHost()) : allowedDomains)
                .build());
        if (crawlStateRepository.tryStartCrawl(requestContext)) {
            crawler.startCrawl(requestContext, new CrawlResultHandler(resultHandler, crawlStateRepository, domainRepository));
        }

        return new SubmitCrawlResponse(requestContext.crawlId());
    }

    public GetCrawlStatusResponse getCrawlStatus(String crawlId) {
        return new GetCrawlStatusResponse(crawlStateRepository.getCrawlStatus(crawlId).orElse(null));
    }

    public void getCrawlResult(String crawlId) {

    }

    @RequiredArgsConstructor
    private static class CrawlResultHandler implements ResultHandler {

        private final Optional<ResultHandler> internalResultHandler;
        private final CrawlStateRepository crawlStateRepository;
        private final DomainRepository domainRepository;

        @Override
        public void onCrawlStarting(CrawlRequestContext context) {
            internalResultHandler.ifPresent(handler -> handler.onCrawlStarting(context));
        }

        @Override
        public void onCrawlProgress(CrawlRequestContext context, URI target, Document document) {
            internalResultHandler.ifPresent(handler -> handler.onCrawlProgress(context, target, document));
            var linkedExternalDomains = document.select("a[href]").stream()
                    .map(link -> link.attr("abs:href"))
                    .map(URI::create)
                    .filter(uri -> uri.getScheme().equalsIgnoreCase("http") || uri.getScheme().equalsIgnoreCase("https"))
                    .map(uri -> UriUtil.getDomainName(uri.getHost()))
                    .filter(domain -> !domain.equalsIgnoreCase(target.getHost()))
                    .map(domain -> new DomainEntity(domain, InternetDomainName.from(domain).topPrivateDomain().toString()))
                    .collect(Collectors.toSet());

            // TODO: Save all external domains with incoming/outgoing. Right now just doing current
            var websiteEntity = domainRepository.findOneByName(UriUtil.getDomainName(target.getHost())).block();
            if (websiteEntity == null) {
                var domainName = UriUtil.getDomainName(target.getHost());
                websiteEntity = new DomainEntity(domainName, InternetDomainName.from(domainName).topPrivateDomain().toString());
            }
            websiteEntity.addOutgoingLinks(linkedExternalDomains);
            domainRepository.save(websiteEntity).block();
            var s = "s";
        }

        @Override
        public void onCrawlFinished(CrawlRequestContext context, CrawlException e) {
            crawlStateRepository.finishCrawl(context, e != null);
            internalResultHandler.ifPresent(handler -> handler.onCrawlFinished(context, e));
            log.info("Finished!", e);
        }

        @Override
        public void onCrawlError(CrawlRequestContext context, URI target, CrawlException e) {
            internalResultHandler.ifPresent(handler -> handler.onCrawlError(context, target, e));
            log.error("Error!", e);
        }
    }
}
