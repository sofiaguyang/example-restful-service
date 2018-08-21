package com.github.sofiaguyang.hateoas

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.ResourceSupport

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY

abstract class HalResource extends ResourceSupport {
    private final Map<String, Object> embedded = new HashMap<String, ResourceSupport>();

    @JsonInclude(NON_EMPTY)
    @JsonProperty("_embedded")
    Map<String, Object> getEmbeddedResources() {
        return embedded
    }

    void embedResource(String relationship, Object resourceToEmbed) {
        embedded.put(relationship, resourceToEmbed)
    }
}
