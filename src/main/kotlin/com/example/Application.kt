package com.example

import com.example.plugins.configureCSRF
import com.example.plugins.configureSerialization
import com.example.plugins.configureStaticZip
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.sse.*
import io.ktor.sse.*
import kotlinx.coroutines.delay


fun main() {
    embeddedServer(Netty, port = 8080) {
        configureSerialization()
        configureSSE()
        configureStaticZip()
        configureCSRF()
    }.start(wait = true)
}




private fun Application.configureSSE() {
    install(SSE)
    routing {
        sse("/sse") {
            repeat(3) {
                val name = call.parameters["name"] ?: "World"
                send(ServerSentEvent(data = "Hello, $name! $it"))
                delay(1000)
            }
            close()
        }
    }
}

