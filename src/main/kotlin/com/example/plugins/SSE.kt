package com.example.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.routing
import io.ktor.server.sse.SSE
import io.ktor.server.sse.sse
import io.ktor.sse.ServerSentEvent
import kotlinx.coroutines.delay

fun Application.configureSSE() {
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