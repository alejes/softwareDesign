package chat.queries

import chat.GetNameQuery

enum class QueryType() {
    GET_STATUS,
    GET_NAME,
    SEND_MESSAGE
}

abstract class Query

object QueriesBuilder {
    @JvmStatic
    fun build(q: QueryType): Query =
            when (q) {
                QueryType.GET_STATUS -> GetStatusQuery()
                QueryType.GET_NAME -> GetNameQuery()
                QueryType.SEND_MESSAGE -> SendMessageQuery()
            }
}





