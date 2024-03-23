package dk.fvtrademarket.api.kt.internal.cache

import java.time.Duration

interface CacheHolder {
    fun clearCache(type: ICacheType)
    fun invalidateCache(type: ICacheType, key: String)
    fun overrideExpiration(type: ICacheType, newExpiration: Duration)
    fun overrideExpiration(type: ICacheType, key: String, newExpiration: Duration)
}