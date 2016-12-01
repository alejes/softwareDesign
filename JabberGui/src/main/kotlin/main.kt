import chat.CmdClient

/**
 * Entry point for command line chat
 * For GUI
 * @see Main
 */
fun main(args: Array<String>) {
    println("Hello in our chat")
    val cmd = CmdClient()
    cmd.work()
}