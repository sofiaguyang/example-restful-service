package com.github.sofiaguyang.example.configuration

import com.github.sofiaguyang.validation.ValidationService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.SmartValidator
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@Configuration
class ApplicationConfig {

    @Configuration
    static class Validation {
        @Bean
        SmartValidator smartValidator() {
            return new LocalValidatorFactoryBean()
        }

        @Bean
        ValidationService validationService() {
            return new ValidationService(smartValidator())
        }
    }
}
