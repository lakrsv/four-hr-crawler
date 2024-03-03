package com.github.lakrsv.crawler.app.repository.result;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Mono;

public interface DomainRepository extends ReactiveNeo4jRepository<DomainEntity, String> {
    Mono<DomainEntity> findOneByName(String name);
}
