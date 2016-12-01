package chat.reponses

import chat.ChatMessage
import chat.ChatUserData
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*
import java.util.*

class ResponseBuilderTest {
    private val gson = Gson()
    private val cud = ChatUserData("MyName", 123, "BBBAAAAM!")

    @Test
    fun statusRequest() {
        val response = ResponseBuilder.build("""{"type": "getStatus"}""", cud).generateAnswer()
        val parsedResponse = gson.fromJson<Map<String, String>>(response)
        assertTrue(parsedResponse.containsKey("status"))
        assertFalse(parsedResponse.containsKey("error"))
        assertEquals("BBBAAAAM!", parsedResponse["status"]);
    }

    @Test
    fun nameRequest() {
        val response = ResponseBuilder.build("""{"type": "getName"}""", cud).generateAnswer()
        val parsedResponse = gson.fromJson<Map<String, String>>(response)
        assertTrue(parsedResponse.containsKey("name"))
        assertFalse(parsedResponse.containsKey("error"))
        assertEquals("MyName", parsedResponse["name"]);
    }

    @Test
    fun messageRequest() {
        val response = ResponseBuilder.build("""{"type": "sendMessage", "port": "22", "name": "MyName2", "text" : "MESSage"}""", cud).generateAnswer()
        val parsedResponse = gson.fromJson<Map<String, String>>(response)
        assertTrue(parsedResponse.containsKey("result"))
        assertFalse(parsedResponse.containsKey("error"))
        assertEquals("ok", parsedResponse["result"]);
    }

    @Test
    fun messageReceive() {
        ResponseBuilder.build("""{"type": "sendMessage", "port": "22", "name": "MyName2", "text" : "MESSage"}""", cud).generateAnswer()
        val queue: Queue<ChatMessage> = cud.messages
        val message = queue.poll()
        assertNotNull(message)
        assertTrue(queue.isEmpty())
        assertEquals("MESSage", message.message)
        assertEquals(22, message.port)
        assertEquals("MyName2", message.author)
    }
}