package com.github.lakrsv.crawler.app.repository.result;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("Domain")
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DomainEntity {
    @Id
    @EqualsAndHashCode.Include
    private final String name;
    private final String topPrivateDomain;
    @Relationship(type = "LINKS_TO", direction = Relationship.Direction.OUTGOING)
    private Set<DomainEntity> outgoingLinks = new HashSet<>();

    public DomainEntity(String name, String topPrivateDomain) {
        this.name = name;
        this.topPrivateDomain = topPrivateDomain;
    }

    public void addOutgoingLinks(Set<DomainEntity> outgoing) {
        outgoingLinks.addAll(outgoing);
    }
}
