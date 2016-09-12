package commands

import Command
import Stream
import java.io.File

/**
 *  view of native cat command
 *  @see Command
 */

class Cat(stream: Stream, input: Stream, tail: String) : Command(stream, input, tail) {
    override val name = "cat"
    override val isNative = true

    override fun execute(): Command {
        val sources: List<String> = when {
            arguments.size > 0 -> arguments.map { it.eval() }
            else -> listOf(input.toString())
        }

        val newStream = Stream(sources.map { File(it) }
                .map { it.readLines().joinToString(separator = " ") }
                .joinToString(separator = " "))
        return Command.parseCommand(newStream, tail)
    }
}
