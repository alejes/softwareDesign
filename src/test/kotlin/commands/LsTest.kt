package commands

import Stream
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.rules.TemporaryFolder

class LsTest {

    @Test
    fun execute() {
        val folder = TemporaryFolder()
        folder.create()

        val rootDir = folder.newFolder("root")

        System.setProperty("user.dir", rootDir.absolutePath)

        val cmd = Ls(Stream(""), Stream(), "")
        assertEquals("", cmd.execute().stream.text)

        rootDir.resolve("main.kt").createNewFile()
        assertEquals("main.kt", cmd.execute().stream.text)

        rootDir.resolve("newFolder").mkdir()
        assertEquals("main.kt\nnewFolder", cmd.execute().stream.text)
    }

}