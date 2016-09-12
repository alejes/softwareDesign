package commands

import Command
import Stream

/**
 *  view of native exit command
 *  @see Command
*/

class Exit(stream: Stream, input: Stream = Stream(), tail: String = "") : Command(stream, input, tail) {
    override val name = "exit"
    override val isNative = true

    override fun execute() =
            Termination(stream)

}