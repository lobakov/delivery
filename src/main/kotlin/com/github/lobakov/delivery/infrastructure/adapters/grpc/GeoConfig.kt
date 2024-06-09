package com.github.lobakov.delivery.infrastructure.adapters.grpc

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "geo")
class GeoConfig {
    lateinit var url: String
    lateinit var port: String
}
