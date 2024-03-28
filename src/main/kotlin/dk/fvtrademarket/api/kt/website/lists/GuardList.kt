package dk.fvtrademarket.api.kt.website.lists

class GuardList(success: Boolean, val item: GuardList.Item) {
    init {
        require(success) { "The request was not successful" }
    }

    data class Item(
        val direktor: List<User>,
        val inspektor: List<User>,
        val viceinspektor: List<User>,
        val officer: List<User>,
        val avagt: List<User>,
        val bvagt: List<User>,
        val cvagt: List<User>,
        val pvagt: List<User>
    )
}