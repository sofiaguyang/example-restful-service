package com.github.sofiaguyang.example.config

import io.micronaut.context.annotation.Bean as MBean
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Infrastructure
import io.micronaut.context.annotation.Prototype
import io.micronaut.runtime.context.scope.Refreshable
import io.micronaut.runtime.context.scope.ThreadLocal
import io.micronaut.spring.beans.MicronautBeanProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import javax.inject.Singleton

@Configuration
class MicronautConfig {

    @Bean
    MicronautBeanProcessor micronautBeanProcessor() {
        return new MicronautBeanProcessor(Singleton, Context, Prototype, Infrastructure, ThreadLocal, Refreshable, MBean)
    }
}
