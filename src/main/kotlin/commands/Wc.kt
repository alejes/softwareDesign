package commands

import Command
import Stream

/**
 * view of native wc command
 * @see Command
 */

class Wc(stream: Stream, input: Stream, tail: String) : Command(stream, input, tail) {
    override val name = "wc"
    override val isNative = true

    override fun execute(): Command {
        val newStream = Stream(stream.toString().split('\n').count().toString() + " " + stream.toString().split(Regex("[ \t\n]")).count() + " " + stream.toString().length)
        return Command.parseCommand(newStream, tail)
    }
}