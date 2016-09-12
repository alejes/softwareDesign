package commands

import Command
import Stream
import java.io.File

/**
 *  view of native pwd command
 *  @see Command
*/

class Pwd(stream: Stream, input: Stream, tail: String) : Command(stream, input, tail) {
    override val name = "pwd"
    override val isNative = true

    override fun execute(): Command {
        val newStream = Stream(File(".").absolutePath)
        return Command.parseCommand(newStream, tail)
    }
}