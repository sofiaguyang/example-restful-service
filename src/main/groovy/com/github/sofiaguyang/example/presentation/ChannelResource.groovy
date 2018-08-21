package com.github.sofiaguyang.example.presentation

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.core.Relation

import javax.validation.constraints.NotEmpty

@Relation(collectionRelation = "channels")
class ChannelResource extends ResourceSupport {

    @JsonProperty(value = "id")
    String sid

    @NotEmpty
    String name

    @NotEmpty
    String description
}
