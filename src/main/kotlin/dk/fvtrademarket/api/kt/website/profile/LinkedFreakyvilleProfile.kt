package dk.fvtrademarket.api.kt.website.profile

import java.util.UUID

/**
 * Represents a linked Freakyville profile.
 * This class is used to represent the response from the Freakyville API when requesting a profile.
 *
 * @param success Whether the request was successful.
 * @param discord The Discord account of the profile.
 * @param verifiedAccounts The verified accounts of the profile.
 */
class LinkedFreakyvilleProfile(success: Boolean, private val discord: Discord, private val verifiedAccounts: Array<VerifiedAccount>) {
    init {
        require(success) { "The request was not successful" }
    }

    /**
     * Gets the Discord account of the profile.
     *
     * @return The Discord account of the profile.
     */
    fun getDiscord(): Discord {
        return discord
    }

    /**
     * Gets the verified accounts of the profile.
     *
     * @return The verified accounts of the profile.
     */
    fun getVerifiedAccounts(): Array<VerifiedAccount> {
        return verifiedAccounts
    }

    /**
     * Represents a Discord account.
     *
     * @param id The ID of the account.
     * @param username The username of the account.
     * @param discriminator The discriminator of the account.
     * @param avatar The avatar of the account.
     */
    data class Discord(private val id: String,
                       val username: String,
                       private val discriminator: String,
                       val avatar: String) {
        /**
         * Gets the ID of the account.
         * We return it as a long because that is the type that Discord uses.
         *
         * @return The ID of the account.
         */
        fun getID(): Long {
            return id.toLong()
        }

        /**
         * Gets the discriminator of the account.
         * We return it as a short because it is always no more than 4 digits.
         * This is because Discord's discriminators were 4 digits long until they were "removed", but they still exist just as the value of zero (0).
         * Not all users have migrated their discriminator and that is why we keep it as a short for backwards compatibility.
         *
         * @return The discriminator of the account.
         */
        fun getDiscriminator(): Short {
            return discriminator.toShort()
        }
    }

    /**
     * Represents a verified account.
     *
     * @param user_id The user ID of the account.
     * @param discord_id The Discord ID of the account.
     * @param minecraft_uuid The Minecraft UUID of the account.
     * @param minecraft_name The Minecraft name of the account.
     * @param primary_account Whether the account is the primary account.
     */
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
