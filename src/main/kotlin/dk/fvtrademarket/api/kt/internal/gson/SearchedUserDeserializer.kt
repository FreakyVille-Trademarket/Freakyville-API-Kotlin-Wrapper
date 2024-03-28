package dk.fvtrademarket.api.kt.internal.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import dk.fvtrademarket.api.kt.website.search.DiscordUser
import dk.fvtrademarket.api.kt.website.search.MinecraftUser
import dk.fvtrademarket.api.kt.website.search.SearchedUser
import java.lang.reflect.Type

class SearchedUserDeserializer : JsonDeserializer<SearchedUser> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): SearchedUser {
        val jsonObject = json.asJsonObject

        return when (jsonObject.get("type").asString) {
            "discordUser" -> context.deserialize<DiscordUser>(json, DiscordUser::class.java)
            "minecraftUser" -> context.deserialize<MinecraftUser>(json, MinecraftUser::class.java)
            else -> throw JsonParseException("Unsupported user type")
        }
    }
}