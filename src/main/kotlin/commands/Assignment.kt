package commands

import Command
import Stream
import Argument
import VariableManager

/**
 *  view of assignment expression
 *  @see Command
*/

class Assignment(stream: Stream, input: Stream, tail: String) : Command(stream, input, tail) {
    override val name = "assignment"
    override val isNative = true

    constructor(arguments: List<Argument>, tail: String) : this(Stream(), Stream(), tail) {
        this.arguments = arguments
    }

    override fun execute(): Command {
        if (arguments.size != 2) {
            return Exit(Stream("assignment require 2 arguments"))
        }
        val result = arguments[1].eval()
        VariableManager[arguments[0].eval()] = result
        return Command.parseCommand(Stream(), tail)
    }
}
