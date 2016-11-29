package chat

import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.JsonObject
import chat.ChatUserData

class SendMessageQuery : Query() {
    fun genQuery(mes : String, cud: ChatUserData): String {
        val obj: JsonObject = jsonObject(
                "type" to "sendMessage",
                "name" to cud.name,
                "text" to mes
        )
        return obj.toString()
    }
}