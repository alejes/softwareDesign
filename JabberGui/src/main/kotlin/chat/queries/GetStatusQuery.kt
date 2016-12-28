package chat.queries

import com.github.salomonbrys.kotson.fromJson
import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.Gson
import com.google.gson.JsonObject

/**
 * query for receive status of another user
 */

class GetStatusQuery : Query() {
    /**
     * generate string representation of status request query
     */
    fun genQuery(): String {
        val obj: JsonObject = jsonObject(
                "type" to "getStatus"
        )
        return obj.toString()
    }

    /**
     * parse status from string representation of query
     */
    fun resolveQuery(str: String): String {
        val gson = Gson()
        val response = gson.fromJson<Map<String, String>>(str)
        return response["status"] ?: "[failed]"
    }
}