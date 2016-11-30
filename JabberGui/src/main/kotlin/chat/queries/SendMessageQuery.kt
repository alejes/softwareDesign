package chat.queries

import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.JsonObject
import chat.ChatUserData
import chat.queries.Query

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