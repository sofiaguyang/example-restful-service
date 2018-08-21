package com.github.sofiaguyang.example.presentation

import com.github.sofiaguyang.validation.ValidationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@Controller
@RequestMapping("/channels")
class ChannelsController {

    @Autowired
    ValidationService validationService

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = HAL_JSON_VALUE)
    ResponseEntity<ChannelResource> create(@RequestBody ChannelResource body) {
        validationService.validate(body)
        return new ResponseEntity<ChannelResource>(body, CREATED)
    }
}
