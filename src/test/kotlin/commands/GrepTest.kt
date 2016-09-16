package commands

import org.junit.Test
import org.junit.Assert.*
import Stream

class GrepTest {

    @Test
    fun testExecuteExternal() {
        val cmd = Command.parseCommand(Stream("hfkre\njekwfjker Vasya\nvker"), "grep -i vasya")
        val result = Command.executePipeline(cmd)
        assertEquals("jekwfjker Vasya", result.text.orEmpty().trim())
    }

}