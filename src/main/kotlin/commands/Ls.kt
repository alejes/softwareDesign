package commands

import Command
import Stream
import java.io.File

/**
 * List directory
 *  @see Command
 */
class Ls(stream: Stream, input: Stream, tail: String) : Command(stream, input, tail) {
    override val name = "ls"
    override val isNative = true

    override fun execute(): Command {
        val path = System.getProperty("user.dir")
        val dirs = arguments
                .map { it.eval() }
                .filter { File(it).isDirectory }
                .toMutableList()

        if (dirs.size == 0) {
            dirs.add(path)
        }

        val newStream = Stream(dirs
                .flatMap { File(it)
                        .listFiles()
                        .filter { it.isDirectory || it.isFile }
                        .map { it.name } }
                .joinToString(separator = "\n"))

        return Command.parseCommand(newStream, tail)
    }
}
