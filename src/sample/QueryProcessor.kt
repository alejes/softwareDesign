package sample

import chat.ChatClient
import javafx.scene.control.TextArea
import javafx.scene.control.TextField

object QueryProcessor {
    @JvmStatic
    fun processQuery(client: ChatClient, str: String, sendText: TextArea) {
        val cmdParts: List<String> = str.split(" ", limit = 2)
        if (cmdParts.size == 2 ) {
            when (cmdParts[0]) {
                "getStatusOf" -> {
                        val port = cmdParts[1].toInt()
                        val status = "answer"
                        println("Status of ${port} is ${status}")
                }
                "sendMessageTo" -> {
                    val queryParts: List<String> = cmdParts[1].split(" ", limit = 2)
                    if (queryParts.size == 2) {
                        val port = queryParts[0].toInt()
                        val message = queryParts[1]
                        client.sendMessage(port, message)
                    }
                }
            }
        }
    }
}