package dk.fvtrademarket.api.kt.internal.requests

import com.google.gson.GsonBuilder
import dk.fvtrademarket.api.kt.internal.gson.SearchedUserDeserializer
import dk.fvtrademarket.api.kt.website.lists.GuardList
import dk.fvtrademarket.api.kt.website.lists.StaffList
import dk.fvtrademarket.api.kt.website.profile.LinkedFreakyvilleProfile
import dk.fvtrademarket.api.kt.website.search.SearchedUser
import dk.fvtrademarket.api.kt.website.wheel.WheelWrapper
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.Optional

object RequestMaker {
    private val gson = GsonBuilder()
        .registerTypeAdapter(SearchedUser::class.java, SearchedUserDeserializer())
        .create()

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
        try {
            InputStreamReader(url.openStream()).use { reader ->
                return Optional.ofNullable(gson.fromJson(reader, forWhat))
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Optional.empty()
    }

    private fun typeMapper(type: Class<*>): String {
        return when (type) {
            WheelWrapper::class.java -> "wheel/wheel"
            LinkedFreakyvilleProfile::class.java -> "user/discord"
            GuardList::class.java -> "guard"
            StaffList::class.java -> "staff"
            else -> ""
        }
    }
}