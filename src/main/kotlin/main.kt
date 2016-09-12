fun main(args: Array<String>) {

    while (true) {
        val userInput = readLine()?.trim()
        val cmd: Command? = Command.parseCommand(Stream(), userInput)
        val result: Stream = Command.executePipeline(cmd)
        println(result)
    }

}