package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.csrf.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sse.*
import io.ktor.sse.*
import kotlinx.coroutines.delay
import kotlin.io.path.Path


fun main() {
    embeddedServer(Netty, port = 8080) {
        configureSSE()
        configureStaticZip()
        configureCSRF()
    }.start(wait = true)
}

private fun Application.configureCSRF() {
    routing {
        route("/csrf") {

            install(CSRF) {
                allowOrigin("https://localhost:8080")

                originMatchesHost()

                checkHeader("X-CSRF") { csrfHeader ->
                    request.headers[HttpHeaders.Origin]?.let { origin ->
                        csrfHeader == origin.hashCode().toString(32) //1ndrgg9
                    } == true
                }

                onFailure {
                    respondText("Access denied!", status = HttpStatusCode.Forbidden)
                }
            }
            post {
                call.respondText("CSRF check passed!")
            }
        }
    }
}

private fun Application.configureStaticZip() {
    routing {
        staticZip(
            remotePath = "/zip",
            basePath = "base",
            Path("files/text-files.zip")
        ) {
            default("file.txt")

            //modify the response by adding the HTTP Etag header
            modify { path, call ->
                call.response.headers.append(HttpHeaders.ETag, path.fileName.toString())
            }
        }
    }
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

