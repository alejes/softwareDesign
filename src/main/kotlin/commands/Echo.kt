package commands

import Command
import Stream

/**
 *  view of native echo command
 *  @see Command
*/

class Echo(stream: Stream, input: Stream, tail: String) : Command(stream, input, tail) {
    override val name = "echo"
    override val isNative = true

    override fun execute(): Command {
        val newStream = Stream(arguments.map { it.eval() }.joinToString(separator = ""))
        return Command.parseCommand(newStream, tail)
    }
}