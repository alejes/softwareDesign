import java.util.*

class CmdClient {
    val input = Scanner(System.`in`)
    val client = ChatClient()

    fun work() {
        var active = true
        println("Enter help for info")
        while (active) {
            val command = input.next()
            when (command) {
                "help" -> println("setName %name - set your name\n" +
                        "getPort - show your port\n" +
                        "getName - show your name\n" +
                        "getNameOf %port - get name of user on port %port\n" +
                        "setStatus %text - set your status\n" +
                        "getStatus - get your current status\n" +
                        "getStatusOf %port - get current status of user on port %port\n" +
                        "exit - exit from this application\n" +
                        "")
                "getPort" -> println("Your port is ${client.port}")
                "getName" -> println("Your name is ${client.name}")
                "setName" -> {
                    client.name = input.next()
                    println("Your name is ${client.name}")
                }
                "setStatus" -> {
                    client.status = input.next()
                    println("Your status is ${client.status}")
                }
                "exit" -> {
                    client.stopClient()
                    active = false;
                }
                "getStatus" -> println("Your status is ${client.status}")
                "getStatusOf" -> {
                    val port = input.nextInt()
                    val status = client.requestStatus(port)
                    println("Status of ${port} is ${status}")
                }
            }
        }
    }
}