/**
 * Represents a stream of user input, or the flow that is redirected between the commands
 */

class Stream(val text: String? = "") {
    override fun toString() = text.orEmpty()
}
