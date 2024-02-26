package com.github.lakrsv.crawler.app.configuration;

import com.github.lakrsv.crawler.app.configuration.properties.CrawlPersistenceProperties;
import com.github.lakrsv.crawler.app.domain.request.CrawlIdCreator;
import com.github.lakrsv.crawler.app.repository.state.CrawlStateRepository;
import com.github.lakrsv.crawler.app.repository.state.DynamoCrawlStateRepository;
import com.github.lakrsv.crawler.app.result.LoggingResultHandler;
import com.github.lakrsv.crawler.core.result.ResultHandler;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CrawlerServiceConfiguration {
    @Bean
    public CrawlIdCreator crawlIdCreator() {
        return new CrawlIdCreator();
    }

    @Bean
    public ResultHandler resultHandler() {
        return new LoggingResultHandler();
    }

    @Bean
    public CrawlStateRepository crawlStateRepository(DynamoDbTemplate dynamoDbTemplate, CrawlPersistenceProperties crawlPersistenceProperties) {
        return new DynamoCrawlStateRepository(dynamoDbTemplate, crawlPersistenceProperties);
    }
}
