package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.sse.*
import io.ktor.sse.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

fun Application.configureSSE() {
    install(SSE)
    routing {
        sse("/sse") {
            val name = call.parameters["name"] ?: "World"

            //
            generateEvents(name)

            //
            heartbeat()
        }
    }
}

private fun ServerSSESession.generateEvents(param: String) {
    launch {
        flow {
            while (coroutineContext.isActive) {
                val event = ServerSentEvent(data = "Hello, $param")
                emit(event)
                delay(1000)
            }
        }.cancellable().collect { event ->
            try {
                send(event)
            } catch (_: Exception) {
                close()
                this@launch.cancel()
            }
        }
    }
}

private fun ServerSSESession.heartbeat() {
    launch {
        flow {
            while (coroutineContext.isActive) {
                val event = ServerSentEvent(data = "heartbeat")
                emit(event)
                delay(30_000)
            }
        }.cancellable().collect { event ->
            try {
                send(data = event.data)
            } catch (_: Exception) {
                this@launch.cancel()
            }
        }
    }
}

