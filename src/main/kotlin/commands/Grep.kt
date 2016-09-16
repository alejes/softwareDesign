package commands

import com.jshmrsn.karg.Arguments
import com.jshmrsn.karg.RawArguments
import Command
import Stream
import max
import min
import java.io.File


/**
 * The class represents settings for parsing the arguments of grep command.
 */

class GrepArguments(raw: RawArguments) : Arguments(raw, name = "default") {
    val ignoreCase = optionalFlag(
            name = "ignore-case",
            description = "Ignore case distinctions, so that characters that differ only in case match each other. ",
            shortNames = listOf('i'),
            default = false
    )

    val wordRegexp = optionalFlag(
            name = "word-regexp",
            description = "Select only those lines containing matches that form whole words. " +
                    "The test is that the matching substring must either be at the beginning of the line, " +
                    "or preceded by a non-word constituent character. " +
                    "Similarly, it must be either at the end of the line or " +
                    "followed by a non-word constituent character. " +
                    "Word-constituent characters are letters, digits, and the underscore.",
            shortNames = listOf('w'),
            default = false
    )

    val afterContext = optionalParameter(
            name = "after-context",
            description = "Print specific lines of trailing  context  after  matching  lines.",
            default = "0",
            shortNames = listOf('A')
    )

    val regexp = positionalArguments(
            name = "regexp",
            description = "Use this as the pattern.",
            minCount = 1,
            maxCount = 1
    )
}

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
        val arguments = com.jshmrsn.karg.parseArguments(input.text.orEmpty().split(' ').filter { it.isNotBlank() }, ::GrepArguments)
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
            if (builtString.length > 0) {
                builtString.appendln("=============")
            }
            builtString.appendln(getLines(lines, currentLine, min(lastLine + arguments.afterContext.toInt(), lines.size)))
        }

        val newStream = Stream(builtString.toString())
        return Command.parseCommand(newStream, tail)
    }
}