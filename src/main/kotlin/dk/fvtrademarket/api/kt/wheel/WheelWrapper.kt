package dk.fvtrademarket.api.kt.wheel

import java.util.UUID

class WheelWrapper(success: Boolean, private val wheel: Wheel) {
    init {
        require(success) { "WheelWrapper must have success=true" }
    }

    fun getID(): UUID {
        return UUID.fromString(wheel.id)
    }

    fun getOptions(): Array<String> {
        return wheel.options.map { it.option }.toTypedArray()
    }

    fun getWinner(): String {
        return wheel.options[wheel.winner.toInt()].option
    }

    fun getCreatedAt(): String {
        return wheel.createdAt
    }

    /**
     * @deprecated Use the built-in functions of WheelWrapper insted of accessing the Wheel directly
     * @return Wheel
     */
    @Deprecated("Use the built in functions of WheelWrapper insted of accessing the Wheel directly")
    fun getWheel(): Wheel {
        return wheel
    }

    data class Wheel(val id: String, val options: Array<Option>, val winner: String, val createdAt: String) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Wheel

            if (id != other.id) return false
            if (!options.contentEquals(other.options)) return false
            if (winner != other.winner) return false
            if (createdAt != other.createdAt) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id.hashCode()
            result = 31 * result + options.contentHashCode()
            result = 31 * result + winner.hashCode()
            result = 31 * result + createdAt.hashCode()
            return result
        }
    }

    data class Option(val option: String)
}
