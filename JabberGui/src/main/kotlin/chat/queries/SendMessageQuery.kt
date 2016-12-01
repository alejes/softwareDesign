package chat.queries

import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.JsonObject
import chat.ChatUserData

/**
 * Send message to another user
 */

class SendMessageQuery : Query() {
    fun genQuery(mes : String, cud: ChatUserData): String {
        val obj: JsonObject = jsonObject(
                "type" to "sendMessage",
                "name" to cud.name,
                "port" to cud.port,
                "text" to mes
        )
        return obj.toString()
    }
}