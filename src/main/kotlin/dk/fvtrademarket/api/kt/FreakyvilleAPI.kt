package dk.fvtrademarket.api.kt

interface FreakyvilleAPI {
    companion object {
        val instance: FreakyvilleAPI = FreakyvilleAPIImpl()
    }

    fun getWebsiteAPI(): FreakyvilleWebsiteAPI
}