package com.example

import com.example.persistence.UserRepositoryImpl
import com.example.plugins.configureCSRF
import com.example.plugins.configureSSE
import com.example.plugins.configureSerialization
import com.example.plugins.configureStaticZip
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

val userRepository = UserRepositoryImpl()
@Serializable
data class User(val id: String, val name: String, val age: Int)
fun main() {
    embeddedServer(Netty, port = 8080) {
        configureSerialization()
        configureSSE()
        configureStaticZip()
        configureCSRF()
        userRoutes()
    }.start(wait = true)
}

// basic CRUD for the user data class
fun Application.userRoutes() {

}














