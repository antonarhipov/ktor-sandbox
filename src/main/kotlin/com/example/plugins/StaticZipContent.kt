package com.example.plugins

import io.ktor.http.HttpHeaders
import io.ktor.server.application.Application
import io.ktor.server.http.content.staticZip
import io.ktor.server.routing.routing
import kotlin.io.path.Path


fun Application.configureStaticZip() {
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
