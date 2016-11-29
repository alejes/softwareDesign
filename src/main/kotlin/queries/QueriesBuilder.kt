package queries

import com.github.salomonbrys.kotson.fromJson
import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.Gson
import com.google.gson.JsonObject

enum class QueryType() {
    GET_STATUS,
    GET_NAME
}

object QueriesBuilder {
    @JvmStatic
    fun build(q: QueryType): Query =
            when (q) {
                QueryType.GET_STATUS -> GetStatusQuery()
                QueryType.GET_NAME -> TODO()
            }
}

abstract class Query {}

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


