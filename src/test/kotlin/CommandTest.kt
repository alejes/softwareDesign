import commands.*
import org.junit.Assert.*
import org.junit.Test

class CommandTest {
    @Test
    fun parseCommandTest() {
        assert(Command.parseCommand(Stream(), "wc dfewdwe | wedwe") is Wc)
        assert(Command.parseCommand(Stream(), "exit dfewdwe | wedwe") is Exit)
        assert(Command.parseCommand(Stream(), "cat dfewdwe | wedwe") is Cat)
        assert(Command.parseCommand(Stream(), "pwd dfewdwe | wedwe") is Pwd)
        assert(Command.parseCommand(Stream(), "echo \$kflwe dfewdwe | wedwe") is Echo)
    }

}