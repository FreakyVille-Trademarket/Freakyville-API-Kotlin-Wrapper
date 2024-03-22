package dk.fvtrademarket.api.kt

import dk.fvtrademarket.api.kt.internal.RequestMaker
import dk.fvtrademarket.api.kt.wheel.WheelWrapper

class FreakyvilleWebsiteAPI {
    companion object {
        val instance: FreakyvilleWebsiteAPI = FreakyvilleWebsiteAPI()
    }

    fun getWheelOfFortune(uniqueId: String, asynchronously: Boolean = false): WheelWrapper {
        return if (asynchronously) {
            RequestMaker.makeRequestAsync(WheelWrapper::class.java, uniqueId).join().orElseThrow{ IllegalArgumentException("Could not get the Wheel of Fortune") } as WheelWrapper
        } else {
            RequestMaker.makeRequest(WheelWrapper::class.java, uniqueId).orElseThrow { IllegalArgumentException("Could not get the Wheel of Fortune") } as WheelWrapper
        }
    }
}
