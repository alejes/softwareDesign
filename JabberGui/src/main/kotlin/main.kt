import chat.CmdClient
import chat.queries.GetStatusQuery
import java.util.*
import java.util.logging.Logger

fun main(args: Array<String>) {
    println("Hello in our chat")
    val cmd = CmdClient()
    cmd.work()
}