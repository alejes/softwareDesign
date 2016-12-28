package chat.queries

import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.JsonObject
import chat.ChatUserData

/**
 * query for send message to another user
 */

class SendMessageQuery : Query() {
    /**
     * generate string representation of send message query
     */
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