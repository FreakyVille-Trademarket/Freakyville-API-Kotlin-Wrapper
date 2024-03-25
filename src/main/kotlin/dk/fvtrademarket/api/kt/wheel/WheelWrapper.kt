package dk.fvtrademarket.api.kt.wheel

import java.util.UUID

/**
 * Represents a spinny wheel from the Freakyville website.
 * This class is used to represent the response from the Freakyville API when requesting a wheel.
 *
 * @param success Whether the request was successful.
 * @param wheel The wheel.
 */
class WheelWrapper(success: Boolean, private val wheel: Wheel) {
    init {
        require(success) { "WheelWrapper must have success=true" }
    }

    /**
     * @return The ID of the wheel as a UUID.
     */
    fun getID(): UUID {
        return UUID.fromString(wheel.id)
    }

    /**
     * @return The options of the wheel.
     */
    fun getOptions(): Array<String> {
        return wheel.options.map { it.option }.toTypedArray()
    }

    /**
     * @return The winning option as it is shown on the wheel.
     */
    fun getWinner(): String {
        return wheel.options[wheel.winner.toInt()].option
    }

    /**
     * @return The time the wheel was created.
     */
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

    /**
     * Represents the spinny wheel from the request.
     *
     * @param id The ID of the wheel.
     * @param options The options of the wheel.
     * @param winner The winner of the wheel.
     * @param createdAt The time the wheel was created.
     */
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

    /**
     * Represents an option on the wheel.
     * It is only needed to represent the response from the Freakyville API.
     */
    data class Option(val option: String)
}
