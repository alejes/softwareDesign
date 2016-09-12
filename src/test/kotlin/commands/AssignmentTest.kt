package commands

import org.junit.Test

import org.junit.Assert.*
import Stream

class AssignmentTest {

    @Test
    fun testExecute() {
        val cmd = Command.parseCommand(Stream(), "frfre=\"dew\" | echo \$frfre")
        val result = Command.executePipeline(cmd)
        assertEquals("dew", result.text)
    }
}