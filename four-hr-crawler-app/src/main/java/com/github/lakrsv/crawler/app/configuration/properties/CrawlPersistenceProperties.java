package com.github.lakrsv.crawler.app.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "persistence")
@Data
public class CrawlPersistenceProperties {
    private Duration crawlStateTimeToLive;
}
