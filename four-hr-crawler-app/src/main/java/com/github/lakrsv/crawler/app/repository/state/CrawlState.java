package com.github.lakrsv.crawler.app.repository.state;

import lombok.Data;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@DynamoDbBean
@Data
public class CrawlState {
    @Getter(onMethod_ = {@DynamoDbPartitionKey})
    private String crawlId;
    private LocalDateTime createdTime;
    private LocalDateTime finishedTime;
    private CrawlStatus status;
    private long ttl;

    public CrawlState() {
    }

    public CrawlState(String crawlId, LocalDateTime createdTime, LocalDateTime expiryTime) {
        this.crawlId = crawlId;
        this.createdTime = createdTime;
        this.finishedTime = null;
        this.status = CrawlStatus.IN_PROGRESS;
        this.ttl = expiryTime.toInstant(ZoneOffset.UTC).getEpochSecond();
    }
}
