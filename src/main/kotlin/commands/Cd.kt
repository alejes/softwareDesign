package commands

import Command
import Stream
import java.io.File
import java.nio.file.Paths

/**
 * Change directory
 * @see Command
 */
class Cd(stream: Stream, input: Stream, tail: String) : Command(stream, input, tail) {
    override val name = "cd"
    override val isNative = true

    override fun execute(): Command {
        val currentPath = System.getProperty("user.dir")

        val dirs = arguments
                .map { it.eval() }
                .toList()

        if (dirs.size < 1) {
            return Exit(Stream("cd should have 1 argument"))
        }


        val newPath = Paths.get(currentPath).resolve(dirs[0]).normalize().toAbsolutePath().toString()
        if (!File(newPath).isDirectory) {
            return Exit(Stream("cd: directory not found ${dirs[0]}"))
        }

        System.setProperty("user.dir", newPath)
        return Command.parseCommand(Stream(), tail)
    }
}