package com.github.lakrsv.crawler.app.repository.state;

import com.github.lakrsv.crawler.app.configuration.properties.CrawlPersistenceProperties;
import com.github.lakrsv.crawler.core.dto.CrawlRequestContext;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@RequiredArgsConstructor
public class DynamoCrawlStateRepository implements CrawlStateRepository {
    private final DynamoDbTemplate dynamoDbTemplate;
    private final CrawlPersistenceProperties crawlStateProperties;

    public boolean tryStartCrawl(CrawlRequestContext context) {
        var currentState = dynamoDbTemplate.load(Key.builder().partitionValue(context.crawlId()).build(), CrawlState.class);
        // TODO: Allow resuming requests that have errored out
        // TODO: Why doesn't save throw (USE KEY CONSTRAINT INSTEAD)
        if (currentState != null) {
            return false;
        }
        var currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        var expiryTime = currentTime.plus(crawlStateProperties.getCrawlStateTimeToLive());
        var crawlState = new CrawlState(context.crawlId(), currentTime, expiryTime);
        dynamoDbTemplate.save(crawlState);
        return true;
    }

    @Override
    public boolean finishCrawl(CrawlRequestContext context, boolean error) {
        var currentState = dynamoDbTemplate.load(Key.builder().partitionValue(context.crawlId()).build(), CrawlState.class);
        if (currentState == null) {
            return false;
        }
        currentState.setFinishedTime(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
        currentState.setStatus(error ? CrawlStatus.FINISHED_ERR : CrawlStatus.FINISHED_OK);
        dynamoDbTemplate.update(currentState);
        return true;
    }

    @Override
    public Optional<CrawlStatus> getCrawlStatus(String crawlId) {
        var currentState = dynamoDbTemplate.load(Key.builder().partitionValue(crawlId).build(), CrawlState.class);
        if (currentState == null) {
            return Optional.empty();
        }
        return Optional.of(currentState.getStatus());
    }
}
