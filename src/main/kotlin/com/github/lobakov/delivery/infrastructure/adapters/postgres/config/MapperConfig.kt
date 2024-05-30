package com.github.lobakov.delivery.infrastructure.adapters.postgres.config

import com.github.lobakov.delivery.infrastructure.adapters.postgres.courier.mapper.CourierMapper
import com.github.lobakov.delivery.infrastructure.adapters.postgres.order.mapper.OrderMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MapperConfig {

    @Bean
    fun orderMapper(): OrderMapper {
        return OrderMapper()
    }

    @Bean
    fun courierMapper(): CourierMapper {
        return CourierMapper()
    }
}
