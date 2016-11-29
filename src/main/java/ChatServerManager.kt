import reponses.ResponseBuilder
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.ServerSocket
import java.util.logging.Level
import java.util.logging.Logger

class ChatServerManager(private val server: ServerSocket, private val cud: ChatUserData) : Runnable {
    private val log: Logger

    init {
        log = Logger.getLogger(ChatServerManager::class.java.name)
    }

    override fun run() {
        try {
            while (!server.isClosed && !Thread.interrupted()) {
                val socket = server.accept()
                log.finest("new client connected from " + socket.port + " to " + server.localPort)
                DataOutputStream(socket.outputStream).use { dos ->
                    DataInputStream(socket.inputStream).use { dis ->
                        val input = dis.readUTF()
                        val response = ResponseBuilder.build(input, cud)
                        dos.writeUTF(response.generateAnswer())
                        //return q.resolveQuery(dis.readUTF())
                    }
                }
            }
        } catch (e: IOException) {
            log.log(Level.SEVERE, e.message, e)
        }

    }
}
