package com.github.lobakov.delivery.core.ports.geo

import com.github.lobakov.delivery.core.domain.sharedkernel.Location

fun interface GeoService {

    suspend fun getGeoLocation(address: String): Location
}