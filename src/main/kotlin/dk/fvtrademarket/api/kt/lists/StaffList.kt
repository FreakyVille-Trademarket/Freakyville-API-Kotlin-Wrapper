package dk.fvtrademarket.api.kt.lists

class StaffList(success: Boolean, val item: StaffList.Item) {
    init {
        require(success) { "The request was not successful" }
    }

    data class Item(
        val owner: List<User>,
        val headAdmin: List<User>,
        val admin: List<User>,
        val moderator: List<User>,
        val helper: List<User>,
        val builderLeader: List<User>,
        val builder: List<User>,
    )
}