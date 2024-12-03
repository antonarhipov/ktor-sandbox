package com.example

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.sse.*
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds


fun main() {
    val client = HttpClient(CIO) {
        install(SSE) {
            reconnectionTime = 5000.milliseconds
            showRetryEvents()
            showCommentEvents()
        }
    }

    runBlocking {
        client.sse(host = "127.0.0.1", port = 8080, path = "/sse") {
//            while (true) {
                incoming.collect { event ->
                    if (event.data != "heartbeat") { // skip heartbeats
                        println(event)
                    }
                }
//            }
        }
    }
}
