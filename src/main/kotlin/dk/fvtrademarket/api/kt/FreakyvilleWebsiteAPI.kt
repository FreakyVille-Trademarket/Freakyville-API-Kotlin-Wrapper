package dk.fvtrademarket.api.kt

import dk.fvtrademarket.api.kt.internal.RequestMaker
import dk.fvtrademarket.api.kt.profile.LinkedFreakyvilleProfile
import dk.fvtrademarket.api.kt.wheel.WheelWrapper
import java.util.UUID

class FreakyvilleWebsiteAPI {
    companion object {
        val instance: FreakyvilleWebsiteAPI = FreakyvilleWebsiteAPI()
    }

    fun getWheelOfFortune(uniqueId: String): WheelWrapper {
        require(uniqueId.length == 36) { "The provided uniqueId must be a UUID (36 chars long)" }
        return RequestMaker.makeRequest(WheelWrapper::class.java, uniqueId).orElseThrow { IllegalArgumentException("Could not get the Wheel of Fortune") } as WheelWrapper
    }

    fun getWheelOfFortune(uniqueId: UUID): WheelWrapper {
        return getWheelOfFortune(uniqueId.toString())
    }

    fun getLinkedProfile(discordId: String): LinkedFreakyvilleProfile {
        return RequestMaker.makeRequest(LinkedFreakyvilleProfile::class.java, discordId).orElseThrow { IllegalArgumentException("Could not get the Linked Profile") } as LinkedFreakyvilleProfile
    }

    fun getLinkedProfile(discordId: Long): LinkedFreakyvilleProfile {
        return getLinkedProfile(discordId.toString())
    }
}
