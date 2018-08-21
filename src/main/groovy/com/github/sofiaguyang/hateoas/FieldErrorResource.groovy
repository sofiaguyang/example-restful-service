package com.github.sofiaguyang.hateoas

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.hateoas.ResourceSupport

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL

class FieldErrorResource extends ResourceSupport {
    String field
    @JsonInclude(NON_NULL)
    String rejectedValue
    String code
    String message
}
