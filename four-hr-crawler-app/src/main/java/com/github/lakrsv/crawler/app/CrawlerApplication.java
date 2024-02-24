package com.github.lakrsv.crawler.app;

import com.github.lakrsv.crawler.app.client.GreetingClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.Attr;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

@SpringBootApplication
@Slf4j
public class CrawlerApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(CrawlerApplication.class, args);
        var greetingClient = context.getBean(GreetingClient.class);
        var dynamoClient = context.getBean(DynamoDbClient.class);
        log.info(dynamoClient.createTable(CreateTableRequest.builder()
                        .tableName("my-table")
                        .keySchema(KeySchemaElement.builder().keyType(KeyType.HASH).attributeName("requestId").build())
                        .attributeDefinitions(AttributeDefinition.builder().attributeName("requestId").attributeType(ScalarAttributeType.S).build())
                        .provisionedThroughput(ProvisionedThroughput.builder().writeCapacityUnits(100L).readCapacityUnits(100L).build())
                .build()).toString());
        log.info("Got greeting client bean");
        log.info(greetingClient.getMessage().block());
    }

}
