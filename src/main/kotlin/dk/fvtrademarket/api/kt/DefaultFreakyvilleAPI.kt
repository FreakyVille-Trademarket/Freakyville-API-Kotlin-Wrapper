package dk.fvtrademarket.api.kt

internal class DefaultFreakyvilleAPI : FreakyvilleAPI {
    override fun getWebsiteAPI(): FreakyvilleWebsiteAPI {
        return FreakyvilleWebsiteAPI()
    }
}
