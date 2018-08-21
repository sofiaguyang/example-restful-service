package com.github.sofiaguyang.example.configuration

import com.github.sofiaguyang.validation.ValidationExceptionControllerAdvice
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PresentationConfig {

    @Configuration
    static class ValidationConfig {

        @Autowired
        MessageSource messageSource

        @Bean
        ValidationExceptionControllerAdvice validationExceptionControllerAdvice() {
            return new ValidationExceptionControllerAdvice({ "logref" }, messageSource)
        }
    }
}
