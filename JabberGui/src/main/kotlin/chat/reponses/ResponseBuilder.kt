package chat.reponses

import chat.ChatMessage
import chat.ChatUserData
import com.github.salomonbrys.kotson.fromJson
import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.Gson

interface Response {
    fun generateAnswer(): String
}

/**
 * Build response to another user
 */

object ResponseBuilder {
    /**
     * build response for query with string representation in q
     */
    @JvmStatic
    fun build(q: String, cud: ChatUserData): Response {
        val gson = Gson()
        val response = gson.fromJson<Map<String, String>>(q)

        when (response["type"]) {
            "getStatus" -> return object : Response {
                override fun generateAnswer() = jsonObject(
                        "status" to cud.status
                ).toString()
            }
            "getName" -> return object : Response {
                override fun generateAnswer() = jsonObject(
                        "name" to cud.name
                ).toString()
            }
            "sendMessage" -> {
                val message = ChatMessage(response["port"]?.toInt() ?: 0, response["name"] ?: "[noname]", (response["text"] ?: "[empty message]"))
                synchronized(cud.messages) {
                    cud.messages.add(message)
                    (cud.messages as java.lang.Object).notifyAll()
                }
                println(message.author + " write: " + message.message)
                return object : Response {
                    override fun generateAnswer() = jsonObject(
                            "result" to "ok"
                    ).toString()
                }
            }
            else -> {
                return object : Response {
                    override fun generateAnswer() = jsonObject(
                            "error" to "unknown input type"
                    ).toString()
                }
            }
        }
    }
}


