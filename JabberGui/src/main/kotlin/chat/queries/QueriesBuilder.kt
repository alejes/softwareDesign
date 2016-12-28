package chat.queries

/**
 * supported types of queries
 */
enum class QueryType() {
    GET_STATUS,
    GET_NAME,
    SEND_MESSAGE
}

abstract class Query

/**
 * Build query for another chat user
 */
object QueriesBuilder {
    @JvmStatic
    fun build(q: QueryType): Query =
            when (q) {
                QueryType.GET_STATUS -> GetStatusQuery()
                QueryType.GET_NAME -> GetNameQuery()
                QueryType.SEND_MESSAGE -> SendMessageQuery()
            }
}






