package chat

import com.github.salomonbrys.kotson.fromJson
import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.Gson
import com.google.gson.JsonObject

class GetStatusQuery : Query() {
    fun genQuery(): String {
        val obj: JsonObject = jsonObject(
                "type" to "getStatus"
        )
        return obj.toString()
    }

    fun resolveQuery(str: String): String {
        val gson = Gson()
        val response = gson.fromJson<Map<String, String>>(str)
        return response["status"] ?: "[failed]"
    }
}