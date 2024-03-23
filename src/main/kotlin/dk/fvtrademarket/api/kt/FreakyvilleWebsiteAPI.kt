package dk.fvtrademarket.api.kt

import dk.fvtrademarket.api.kt.internal.cache.Cache
import dk.fvtrademarket.api.kt.internal.cache.CacheHolder
import dk.fvtrademarket.api.kt.internal.cache.ICacheType
import dk.fvtrademarket.api.kt.internal.requests.RequestMaker
import dk.fvtrademarket.api.kt.profile.LinkedFreakyvilleProfile
import dk.fvtrademarket.api.kt.wheel.WheelWrapper
import java.time.Duration
import java.util.UUID

class FreakyvilleWebsiteAPI: CacheHolder {
    enum class CacheType: ICacheType{
        WHEEL, PROFILE
    }

    private val wheelCache = Cache<String, WheelWrapper>({ uniqueId ->
        RequestMaker.makeRequest(WheelWrapper::class.java, uniqueId).orElseThrow { IllegalArgumentException("Could not get the Wheel of Fortune") } as WheelWrapper
    }, Duration.ofMinutes(5))

    private val profileCache = Cache<String, LinkedFreakyvilleProfile>({ uniqueId ->
        RequestMaker.makeRequest(LinkedFreakyvilleProfile::class.java, uniqueId).orElseThrow { IllegalArgumentException("Could not get the Linked Freakyville Profile") } as LinkedFreakyvilleProfile
    }, Duration.ofMinutes(10))

    fun getWheelOfFortune(uniqueId: String): WheelWrapper {
        require(uniqueId.length == 36) { "The provided unique ID must be a UUID (36 chars long)" }
        return wheelCache.get(uniqueId)
    }

    fun getWheelOfFortune(uniqueId: UUID): WheelWrapper {
        return getWheelOfFortune(uniqueId.toString())
    }

    fun getLinkedProfile(discordId: String): LinkedFreakyvilleProfile {
        require(discordId.toLongOrNull() != null) { "The provided discord ID must be a Long" }
        return profileCache.get(discordId)
    }

    fun getLinkedProfile(discordId: Long): LinkedFreakyvilleProfile {
        return getLinkedProfile(discordId.toString())
    }

    override fun clearCache(type: ICacheType) {
        when (type) {
            CacheType.WHEEL -> wheelCache.clear()
            CacheType.PROFILE -> profileCache.clear()
            else -> throw IllegalArgumentException("Cache type of $type is not supported in this API")
        }
    }

    override fun invalidateCache(type: ICacheType, key: String) {
        when (type) {
            CacheType.WHEEL -> wheelCache.invalidate(key)
            CacheType.PROFILE -> profileCache.invalidate(key)
            else -> throw IllegalArgumentException("Cache type of $type is not supported in this API")
        }
    }

    override fun overrideExpiration(type: ICacheType, newExpiration: Duration) {
        when (type) {
            CacheType.WHEEL -> wheelCache.overrideExpiration(newExpiration)
            CacheType.PROFILE -> profileCache.overrideExpiration(newExpiration)
            else -> throw IllegalArgumentException("Cache type of $type is not supported in this API")
        }
    }

    override fun overrideExpiration(type: ICacheType, key: String, newExpiration: Duration) {
        when (type) {
            CacheType.WHEEL -> wheelCache.overrideExpiration(key, newExpiration)
            CacheType.PROFILE -> profileCache.overrideExpiration(key, newExpiration)
            else -> throw IllegalArgumentException("Cache type of $type is not supported in this API")
        }
    }
}
