package com.github.lobakov.delivery.infrastructure.adapters.grpc.geo

import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.ports.geo.GeoService
import com.github.lobakov.delivery.infrastructure.adapters.grpc.config.GeoConfig
import com.github.lobakov.delivery.infrastructure.adapters.grpc.geo.GeoGrpcKt.GeoCoroutineStub
import io.grpc.ManagedChannelBuilder
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.naming.ServiceUnavailableException

@Service
class GeoServiceImpl(
    config: GeoConfig
): GeoService {

    private val channel = ManagedChannelBuilder
        .forAddress(
            config.url,
            config.port.toInt()
        )
        .build()
    private val stub = GeoCoroutineStub(channel)

    override suspend fun getGeoLocation(address: String): Location {
        val request = getGeolocationRequest {
            this.address = address
        }

        val response = try {
             stub.getGeolocation(request).location
        } catch (e: Exception) {
            LOGGER.error("Failed to send order status changed event, error: {}", e.localizedMessage)
            throw ServiceUnavailableException("Just to point out that we process errors")
        }

        return Location(response.x, response.y)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(GeoService::class.java)
    }
}
