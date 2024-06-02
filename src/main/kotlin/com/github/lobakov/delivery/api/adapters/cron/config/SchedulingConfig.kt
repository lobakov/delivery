package com.github.lobakov.delivery.api.adapters.cron.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@Configuration
@ConfigurationProperties(prefix = "cron")
class SchedulingConfig {
    lateinit var assignOrders: String
    lateinit var moveCouriers: String
}
