import commands.*
import java.io.BufferedReader
import java.io.InputStreamReader


/**
 * The class represents on the essence of the command that will be run by native or external bash
 * @property stream the data redirected to stdin
 * @property input raw data passed as arguments to the command
 * @property tail the remainder of the commands in the current pipeline
 */

abstract class Command(val stream: Stream, val input: Stream, protected val tail: String) {
    /**
     * @property arguments parsed of current command
     * @property isNative - type of command internal or external which are executed in another bash.
     * @property name - command identification string
     */
    var arguments: List<Argument> = parseArguments(input)
    abstract val isNative: Boolean
    abstract val name: String

    /**
     * Execute current command and
     * @return following command.
     */
    abstract fun execute(): Command

    /**
     * Parsing [input] into format, applicable for [arguments]
     */
    protected fun parseArguments(argument: Stream?): List<Argument> =
            argument?.text?.split(Regex(""""((\$[\w,\.!\$\n\s ]*) | ("[\w,\.!\$\n\s ]*"))/gx"""))?.map { Argument(it) } ?: listOf()


    companion object {
        /**
         *  parse command in [cmd] and application [stream] to stdin
         */
        fun parseCommand(stream: Stream, cmd: String?): Command {
            val command = cmd?.trim()
            val input = command?.substringAfter(' ', missingDelimiterValue = "")?.substringBefore('|')
            val tail = command?.substringAfter('|', missingDelimiterValue = "")

            return when (command?.substringBefore(' ')) {
                "wc" -> Wc(stream, Stream(input), tail!!)
                "echo" -> Echo(stream, Stream(input), tail!!)
                "pwd" -> Pwd(stream, Stream(input), tail!!)
                "cat" -> Cat(stream, Stream(input), tail!!)
                "grep" -> Grep(stream, Stream(input), tail!!)
                "exit" -> Exit(stream, Stream(input), tail!!)
                else -> {
                    if (command?.contains('=') ?: false) {
                        Assignment(
                                listOf(
                                        Argument(cmd!!.substringBefore('=')),
                                        Argument(cmd.substringAfter('='))
                                ), tail!!)
                    } else {
                        val activeCommand = cmd?.substringBefore('|')
                        if (activeCommand?.isEmpty() ?: true) {
                            Termination(stream, Stream(input))
                        } else {
                            val process = Runtime.getRuntime().exec(activeCommand)
                            val rd = BufferedReader(InputStreamReader(process.inputStream))
                            return parseCommand(Stream(rd.readText()), tail)
                        }
                    }
                }
            }
        }

        /**
         * the full executing of the pipeline from the command [cmd]
         * @return stdout of calculations
         */
        fun executePipeline(cmd: Command?): Stream {
            var currentCmd = cmd
            while (currentCmd !is Termination) {
                currentCmd = currentCmd!!.execute()
            }
            return currentCmd.stream
        }
    }
}