package chat

import chat.ChatUserData
import com.github.salomonbrys.kotson.fromJson
import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.Gson

interface Response {
    fun generateAnswer(): String
}

object ResponseBuilder {
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
                println((response["name"] ?: "[noname]") + " write: " + (response["text"] ?: "[empty message]"))
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


