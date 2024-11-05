package com.example.persistence

import com.example.User


interface UserRepository {
    fun getAll(): List<User>

    fun get(id: String): User?

    fun add(user: User)

    fun update(id: String, user: User)

    fun delete(id: String)
}


class UserRepositoryImpl : UserRepository {
    override fun getAll(): List<User> {
        TODO("Not yet implemented")
    }

    override fun get(id: String): User? {
        TODO("Not yet implemented")
    }

    override fun add(user: User) {
        TODO("Not yet implemented")
    }

    override fun update(id: String, user: User) {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }
}