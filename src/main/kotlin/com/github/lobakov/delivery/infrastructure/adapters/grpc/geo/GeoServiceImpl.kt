package com.github.lobakov.delivery.infrastructure.adapters.grpc.geo

import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.ports.geo.GeoService
import com.github.lobakov.delivery.infrastructure.adapters.grpc.GeoConfig
import com.github.lobakov.delivery.infrastructure.adapters.grpc.client.GeoGrpcKt.GeoCoroutineStub
import com.github.lobakov.delivery.infrastructure.adapters.grpc.client.getGeolocationRequest
import io.grpc.ManagedChannelBuilder
import org.springframework.stereotype.Service

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

        val response = stub.getGeolocation(request).location

        return Location(response.x, response.y)
    }
}
