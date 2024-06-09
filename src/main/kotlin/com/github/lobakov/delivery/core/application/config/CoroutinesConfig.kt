package com.github.lobakov.delivery.core.application.config

import kotlinx.coroutines.Dispatchers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.coroutines.CoroutineContext

@Configuration
class CoroutinesConfig {

    @Bean
    fun ioContext(): CoroutineContext {
        return Dispatchers.IO
    }
}
