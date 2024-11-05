package com.example.plugins

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.plugins.csrf.CSRF
import io.ktor.server.response.respondText
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing


fun Application.configureCSRF() {
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