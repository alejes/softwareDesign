import queries.GetStatusQuery
import java.util.*
import java.util.logging.Logger

fun main(args: Array<String>) {
    println(GetStatusQuery().genQuery())

    println("Hello in our chat")
    val cmd = CmdClient()
    cmd.work()
}