package commands

import Command
import Stream
import java.nio.file.Paths

class Cd(stream: Stream, input: Stream, tail: String) : Command(stream, input, tail) {
    override val name = "ls"
    override val isNative = true

    override fun execute(): Command {
        val currentPath = System.getProperty("user.dir")
        val newPath = Paths.get(currentPath).resolve(currentPath).normalize().toAbsolutePath().toString()

        System.setProperty("user.dir", newPath)
        return Command.parseCommand(Stream(), tail)
    }
}