package dk.fvtrademarket.api.kt.internal

import dk.fvtrademarket.api.kt.internal.requests.RequestMaker
import dk.fvtrademarket.api.kt.website.profile.LinkedFreakyvilleProfile
import dk.fvtrademarket.api.kt.website.search.DiscordUser
import dk.fvtrademarket.api.kt.website.search.MinecraftUser
import dk.fvtrademarket.api.kt.website.search.PerformedSearch
import java.net.URL
import java.util.Optional

object AdvancedRequestBinder {
    fun findProfileByDiscordName(discordName: String): Optional<LinkedFreakyvilleProfile> {
        val performedSearch: PerformedSearch = RequestMaker.makeRequest(PerformedSearch::class.java,
            URL("https://freakyville.dk/api/search?query=${discordName}&offset=0&limit=25&mc=&created=&createdType=&locations=")
        ).orElseThrow { IllegalArgumentException("Could not perform the search") } as PerformedSearch

        if (performedSearch.items.size > 1) {
            return findBestDiscordCandidate(discordName, performedSearch)
        }
        if (performedSearch.items[0].type != "discordUser") {
            return Optional.empty()
        }
        val bestCandidate: LinkedFreakyvilleProfile = RequestMaker.makeRequest(LinkedFreakyvilleProfile::class.java, (performedSearch.items[0] as DiscordUser).id).orElseThrow { IllegalArgumentException("Could not get the Linked Freakyville Profile") } as LinkedFreakyvilleProfile
        return Optional.of(bestCandidate)
    }

    fun findProfileByMinecraftName(minecraftName: String): Optional<LinkedFreakyvilleProfile> {
        val performedSearch: PerformedSearch = RequestMaker.makeRequest(PerformedSearch::class.java,
            URL("https://freakyville.dk/api/search?query=${minecraftName}&offset=0&limit=25&mc=&created=&createdType=&locations=")
        ).orElseThrow { IllegalArgumentException("Could not perform the search") } as PerformedSearch

        if (performedSearch.items.size > 1) {
            return findBestMCCandidate(minecraftName, performedSearch)
        }
        if (performedSearch.items[0].type != "minecraftUser") {
            return Optional.empty()
        }
        val bestCandidate: LinkedFreakyvilleProfile = RequestMaker.makeRequest(LinkedFreakyvilleProfile::class.java, (performedSearch.items[0] as MinecraftUser).getDiscordId().toString()).orElseThrow { IllegalArgumentException("Could not get the Linked Freakyville Profile") } as LinkedFreakyvilleProfile
        return Optional.of(bestCandidate)
    }

    fun findBestMCCandidate(minecraftName: String, performedSearch: PerformedSearch): Optional<LinkedFreakyvilleProfile> {
        var bestCandidate: LinkedFreakyvilleProfile? = null
        for (item in performedSearch.items) {
            if (item.type != "minecraftUser") {
                continue
            }
            item as MinecraftUser
            if (item.username.equals(minecraftName, ignoreCase = true)) {
                bestCandidate = RequestMaker.makeRequest(LinkedFreakyvilleProfile::class.java, item.getDiscordId().toString()).orElseThrow { IllegalArgumentException("Could not get the Linked Freakyville Profile") } as LinkedFreakyvilleProfile
                break
            }
        }
        return Optional.ofNullable(bestCandidate)
    }

    fun findBestDiscordCandidate(discordName: String, performedSearch: PerformedSearch): Optional<LinkedFreakyvilleProfile> {
        var bestCandidate: LinkedFreakyvilleProfile? = null
        for (item in performedSearch.items) {
            if (item.type != "discordUser") {
                continue
            }
            item as DiscordUser
            if (item.username.equals(discordName, ignoreCase = true)) {
                bestCandidate = RequestMaker.makeRequest(LinkedFreakyvilleProfile::class.java, item.id).orElseThrow { IllegalArgumentException("Could not get the Linked Freakyville Profile") } as LinkedFreakyvilleProfile
                break
            }
        }
        return Optional.ofNullable(bestCandidate)
    }
}