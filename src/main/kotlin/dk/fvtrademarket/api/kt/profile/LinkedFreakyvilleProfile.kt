package dk.fvtrademarket.api.kt.profile

import java.util.UUID

class LinkedFreakyvilleProfile(success: Boolean, private val discord: Discord, private val verifiedAccounts: Array<VerifiedAccount>) {
    init {
        require(success) { "The request was not successful" }
    }

    fun getDiscord(): Discord {
        return discord
    }

    fun getVerifiedAccounts(): Array<VerifiedAccount> {
        return verifiedAccounts
    }

    data class Discord(private val id: String,
                       val username: String,
                       private val discriminator: String,
                       val avatar: String) {
        fun getID(): Long {
            return id.toLong()
        }

        fun getDiscriminator(): Int {
            return discriminator.toInt()
        }
    }
    data class VerifiedAccount(val user_id: Int,
                               private val discord_id: String,
                               private val minecraft_uuid: String,
                               val minecraft_name: String,
                               private val primary_account: Int) {
        fun getDiscordID(): Long {
            return discord_id.toLong()
        }

        fun getMinecraftUUID(): UUID {
            return UUID.fromString(minecraft_uuid)
        }

        fun isPrimaryAccount(): Boolean {
            return primary_account == 1
        }
    }
}
