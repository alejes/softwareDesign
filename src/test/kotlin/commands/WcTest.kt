package commands

import org.junit.Test
import org.junit.Assert.*
import Stream

class WcTest {

    @Test
    fun testExecuteInner() {
        val cmd = Wc(Stream("\"edwdwe\"\n\"eede dewdwe\""), Stream(), "")
        assertEquals("2 3 22", cmd.execute().stream.text)
    }

    @Test
    fun testExecuteExternal() {
        val cmd = Command.parseCommand(Stream("\"edwdwe\"\n\"eede dewdwe\""), "wc")
        val result = Command.executePipeline(cmd)
        assertEquals("2 3 22", result.text)
    }
}