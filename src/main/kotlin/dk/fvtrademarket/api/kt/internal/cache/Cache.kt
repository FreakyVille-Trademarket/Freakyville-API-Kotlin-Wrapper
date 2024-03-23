package dk.fvtrademarket.api.kt.internal.cache

import java.time.Duration
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

class Cache<K, V>(private val loader: (K) -> V, private var expiryDuration: Duration) {
    private data class CacheEntry<V>(val value: V, val expiryTime: Instant)

    private val cache = ConcurrentHashMap<K, CacheEntry<V>>()

    fun get(key: K): V {
        val now = Instant.now()
        return cache.compute(key) { _, entry ->
            if (entry == null || now.isAfter(entry.expiryTime)) {
                val value = loader(key)
                CacheEntry(value, now.plus(expiryDuration))
            } else {
                entry
            }
        }!!.value
    }

    fun invalidate(key: K) {
        cache.remove(key)
    }

    fun clear() {
        cache.clear()
    }

    fun overrideExpiration(key: K, newExpiration: Duration) {
        cache.computeIfPresent(key) { _, entry ->
            CacheEntry(entry.value, Instant.now().plus(newExpiration))
        }
    }

    fun overrideExpiration(newExpiration: Duration) {
        cache.replaceAll { _, entry ->
            CacheEntry(entry.value, Instant.now().plus(newExpiration))
        }
    }
}