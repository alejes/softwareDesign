package commands

import Stream
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.rules.TemporaryFolder

class CdTest() {
    @Test
    fun execute() {
        val folder = TemporaryFolder()
        folder.create()

        val rootDir = folder.newFolder("root")

        System.setProperty("user.dir", rootDir.absolutePath)
        val newDir = rootDir.resolve("newFolder")
        newDir.mkdir()

        Cd(Stream(), Stream("newFolder"), "").execute()
        assertEquals(newDir.absolutePath, System.getProperty("user.dir"))

        Cd(Stream(), Stream(".."), "").execute()
        assertEquals(rootDir.absolutePath, System.getProperty("user.dir"))
    }
}