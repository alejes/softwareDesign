package reponses

import ChatUserData
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

        return when (response["type"]) {
            "getStatus" -> object : Response {
                override fun generateAnswer() = jsonObject(
                        "status" to cud.status
                ).toString()
            }
            "getName" -> object : Response {
                override fun generateAnswer() = jsonObject(
                        "name" to cud.name
                ).toString()
            }
            else -> {
                object : Response {
                    override fun generateAnswer() = jsonObject(
                            "error" to "unknown input type"
                    ).toString()
                }
            }
        }
    }
}


