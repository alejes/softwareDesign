import commands.Exit

fun main(args: Array<String>) {

    var cmd: Command? = null
    while (cmd !is Exit) {
        val userInput = readLine()?.trim()
        cmd = Command.parseCommand(Stream(), userInput)
        val result: Stream = Command.executePipeline(cmd)
        println(result)
    }

}