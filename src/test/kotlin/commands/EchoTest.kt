package commands

import org.junit.Test
import org.junit.Assert.*
import Stream


class EchoTest {

    @Test
    fun testExecuteInner() {
        val cmd = Echo(Stream(), Stream("\"edwdwe\""), "")
        assertEquals("edwdwe", cmd.execute().stream.text)
    }

    @Test
    fun testExecuteExternal() {
        val cmd = Command.parseCommand(Stream(), "echo \"edwdwe\"")
        val result = Command.executePipeline(cmd)
        assertEquals("edwdwe", result.text)
    }

}