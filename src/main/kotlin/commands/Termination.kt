package commands

import Command
import Stream

/**
 * view of command that mark end of evaluation
 * @see Command
*/

class Termination(stream: Stream = Stream(), input: Stream = Stream(), tail: String = "") : Command(stream, input, tail) {
    override val isNative = true
    override val name = "termination"

    override fun execute(): Command =
            this

}