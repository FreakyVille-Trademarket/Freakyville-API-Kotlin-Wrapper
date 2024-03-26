package dk.fvtrademarket.api.kt.search

class PerformedSearch(success: Boolean, val items: List<SearchedUser>) {
    init {
        require(success) { "The request was not successful" }
        require(items.isNotEmpty()) { "The search did not return any results" }
    }
}