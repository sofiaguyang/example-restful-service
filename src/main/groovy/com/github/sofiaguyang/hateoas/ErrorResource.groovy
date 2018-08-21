package com.github.sofiaguyang.hateoas

import com.fasterxml.jackson.annotation.JsonInclude

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY

class ErrorResource extends HalResource {
    @JsonInclude(NON_EMPTY)
    String logref

    @JsonInclude(NON_EMPTY)
    String code

    @JsonInclude(NON_EMPTY)
    String message

    @JsonInclude(NON_EMPTY)
    Integer total
}
