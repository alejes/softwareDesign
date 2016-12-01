package chat


/**
 * One message outcomming/incomming to another user
 */

data class ChatMessage(val port: Int, val author: String, val message: String)