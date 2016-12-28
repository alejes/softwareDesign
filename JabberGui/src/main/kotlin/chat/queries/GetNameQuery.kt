package chat.queries

import com.github.salomonbrys.kotson.fromJson
import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.Gson
import com.google.gson.JsonObject


/**
 * query for receive name of another user
 */
class GetNameQuery : Query() {
    /**
     * generate string representation of name request query
     */
    fun genQuery(): String {
        val obj: JsonObject = jsonObject(
                "type" to "getName"
        )
        return obj.toString()
    }

    /**
     * parse name from string representation of query
     */
    fun resolveQuery(str: String): String {
        val gson = Gson()
        val response = gson.fromJson<Map<String, String>>(str)
        return response["name"] ?: "[failed]"
    }
}