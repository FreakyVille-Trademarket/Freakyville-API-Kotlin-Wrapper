package dk.fvtrademarket.api.kt

interface FreakyvilleAPI {
    companion object {
        var instance: FreakyvilleAPI = DefaultFreakyvilleAPI()
    }

    fun getWebsiteAPI(): FreakyvilleWebsiteAPI
}