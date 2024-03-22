package dk.fvtrademarket.api.kt

class FreakyvilleAPIImpl : FreakyvilleAPI {
    override fun getWebsiteAPI(): FreakyvilleWebsiteAPI {
        return FreakyvilleWebsiteAPI.instance
    }
}
