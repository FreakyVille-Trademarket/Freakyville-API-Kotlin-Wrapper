package dk.fvtrademarket.api.kt

import dk.fvtrademarket.api.kt.website.FreakyvilleWebsiteAPI

internal class DefaultFreakyvilleAPI : FreakyvilleAPI {
    override fun getWebsiteAPI(): FreakyvilleWebsiteAPI {
        return FreakyvilleWebsiteAPI()
    }
}
