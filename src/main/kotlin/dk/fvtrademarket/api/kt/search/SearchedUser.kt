package dk.fvtrademarket.api.kt.search

interface SearchedUser {
    val type: String
    val username: String
}


data class DiscordUser(
    override val type: String,
    val id: String,
    override val username: String,
    val discriminator: String,
    val avatar: String
) : SearchedUser

data class MinecraftUser(
    override val type: String,
    override val username: String,
    val uuid: String,
    private val primary: Int,
    private val discord_id: String
) : SearchedUser {
    fun isPrimary(): Boolean {
        return primary == 1
    }

    fun getDiscordId(): Long {
        return discord_id.toLong()
    }
}
