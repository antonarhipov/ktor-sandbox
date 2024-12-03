package com.example

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.sse.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.runBlocking


fun main() {
    val client = HttpClient(CIO) {
        install(SSE)
    }

    runBlocking {
        client.sse(host = "127.0.0.1", port = 8080, path = "/sse") {
            incoming.collect { event ->
                if (event.data != "heartbeat") { // skip heartbeats
                    println(event)
                }
            }
        }
    }
}