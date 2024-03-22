package dk.fvtrademarket.api.kt.internal

import com.google.gson.Gson
import dk.fvtrademarket.api.kt.wheel.WheelWrapper
import java.io.InputStreamReader
import java.net.URL
import java.util.Optional
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException


object RequestMaker {
    /**
     * This method makes a request to the Freakyville api website and returns the result as an Optional.
     * The arguments are appended to the URL of the Freakyville api website. It is recommended to use the other makeRequest method to make it easier to handle a change in the url source.
     *
     * @param forWhat The class to convert the JSON to
     * @param arguments The arguments to append to the URL of the Freakyville api website
     * @return An Optional of the result
     */
    fun makeRequest(forWhat: Class<*>, vararg arguments: String): Optional<Any> {
        val stringedType = typeMapper(forWhat)
        if (stringedType.isEmpty()) {
            throw IllegalArgumentException("That type is not supported!")
        }
        val url = URL("https://freakyville.dk/api/${stringedType}/${arguments.joinToString("/")}")
        return makeRequest(forWhat, url)
    }

    /**
     * This method makes a request to the given URL and returns the result as an Optional.
     *
     * @param forWhat The class to convert the JSON to
     * @param url The URL to make the request to
     * @return An Optional of the result
     */
    fun makeRequest(forWhat: Class<*>, url: URL): Optional<Any> {
        var result: Optional<Any> = Optional.empty()
        try {
            val reader = InputStreamReader(url.openStream())
            result = Optional.ofNullable(Gson().fromJson(reader, forWhat))
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    /**
     * This method makes an async request to the Freakyville api website and returns the result as an Optional.
     * The arguments are appended to the URL of the Freakyville api website.
     * It is recommended to use the other makeRequestAsync method to make it easier to handle a change in the url source.
     * This is due to the fact that this method relies on the typeMapper method to convert the class to a corrected string. If the class is not supported, an IllegalArgumentException is thrown.
     *
     * @param forWhat The class to convert the JSON to
     * @param arguments The arguments to append to the URL of the Freakyville api website
     * @return An Optional of the result
     */
    fun makeRequestAsync(forWhat: Class<*>, vararg arguments: String): CompletableFuture<Optional<Any>> {
        val stringedType = typeMapper(forWhat)
        if (stringedType.isEmpty()) {
            throw IllegalArgumentException("That type is not supported!")
        }
        val url = URL("https://freakyville.dk/api/${stringedType}/${arguments.joinToString("/")}")
        return makeRequestAsync(forWhat, url)
    }

    /**
     * This method makes an async request to the given URL and returns the result as an Optional.
     *
     * @param forWhat The class to convert the JSON to
     * @param url The URL to make the request to
     * @return An Optional of the result
     */
    fun makeRequestAsync(forWhat: Class<*>, url: URL): CompletableFuture<Optional<Any>> {
        return CompletableFuture.supplyAsync {
            try {
                InputStreamReader(url.openStream()).use { reader ->
                    Optional.ofNullable(Gson().fromJson(reader, forWhat))
                }
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }.exceptionally { e ->
            e.printStackTrace()
            Optional.ofNullable(null)
        }
    }

    private fun typeMapper(type: Class<*>): String {
        return when (type) {
            WheelWrapper::class.java -> "wheel"
            else -> ""
        }
    }
}