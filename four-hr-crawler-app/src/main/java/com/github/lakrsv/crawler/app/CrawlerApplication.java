package com.github.lakrsv.crawler.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class CrawlerApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(CrawlerApplication.class, args);
//        var dynamoClient = context.getBean(DynamoDbClient.class);
//        log.info(dynamoClient.createTable(CreateTableRequest.builder()
//                        .tableName("my-table")
//                        .keySchema(KeySchemaElement.builder().keyType(KeyType.HASH).attributeName("requestId").build())
//                        .attributeDefinitions(AttributeDefinition.builder().attributeName("requestId").attributeType(ScalarAttributeType.S).build())
//                        .provisionedThroughput(ProvisionedThroughput.builder().writeCapacityUnits(100L).readCapacityUnits(100L).build())
//                .build()).toString());
    }

}
