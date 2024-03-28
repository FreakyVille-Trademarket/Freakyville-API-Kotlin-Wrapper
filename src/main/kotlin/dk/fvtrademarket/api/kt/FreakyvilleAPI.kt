package dk.fvtrademarket.api.kt

import dk.fvtrademarket.api.kt.website.FreakyvilleWebsiteAPI

interface FreakyvilleAPI {
    companion object {
        var instance: FreakyvilleAPI = DefaultFreakyvilleAPI()
    }

    fun getWebsiteAPI(): FreakyvilleWebsiteAPI
}