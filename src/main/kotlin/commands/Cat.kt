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
        var sources: List<String> = arguments.map { it.eval() }.filter(String::isNotEmpty)
        if (sources.isEmpty()) sources = listOf(stream.toString())

        val newStream = Stream(sources.map(::File)
                .map { it.readLines().joinToString(separator = "\n") }
                .joinToString(separator = " "))
        return Command.parseCommand(newStream, tail)
    }
}
