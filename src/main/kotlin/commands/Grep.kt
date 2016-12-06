package commands

import Command
import Stream
import min

/**
 *  view of native grep command
 *  @see Command
 */

class Grep(stream: Stream, input: Stream, tail: String) : Command(stream, input, tail) {
    override val name = "grep"
    override val isNative = true

    /**
     * To print the lines from [lines]  starting with [from] and up to [to]
     */
    private fun getLines(lines: List<String>, from: Int, to: Int): StringBuilder {
        val build = StringBuilder();
        for (i in from - 1..to - 1) {
            build.appendln(lines[i])
        }
        return build
    }

    override fun execute(): Command {
        val arguments: GrepArguments = com.jshmrsn.karg.parseArguments(input.text.orEmpty().split(' ').filter { it.isNotBlank() }, ::GrepArguments)
        val options = mutableSetOf<RegexOption>();
        if (arguments.ignoreCase) {
            options.add(RegexOption.IGNORE_CASE)
        }

        val pattern = if (arguments.wordRegexp) "\\b" + arguments.regexp.first() + "\\b" else arguments.regexp.first()

        val entity = pattern.toRegex(options).findAll(stream.text.orEmpty()).toList()
        val lines = stream.text.orEmpty().lines()

        val builtString = StringBuilder()
        var currentLine = 0
        var currentPosition = 1
        for (entry in entity) {
            /**
             * Map entry.range to lines in original input
             */
            while (entry.range.first > currentPosition) {
                currentPosition += lines[currentLine].length
                currentLine++
            }

            var lastLine = currentLine
            var lastPosition = currentPosition + lines[lastLine - 1].length
            while (entry.range.last > lastPosition) {
                lastPosition += lines[lastLine].length
                lastLine++
            }
            if (builtString.isNotEmpty()) {
                builtString.appendln("=============")
            }
            builtString.appendln(getLines(lines, currentLine, min(lastLine + arguments.afterContext.toInt(), lines.size)))
        }

        val newStream = Stream(builtString.toString())
        return Command.parseCommand(newStream, tail)
    }
}