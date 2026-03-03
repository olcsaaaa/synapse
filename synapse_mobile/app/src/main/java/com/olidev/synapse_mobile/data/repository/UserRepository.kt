package com.olidev.synapse_mobile.data.repository

import com.olidev.synapse_mobile.data.local.daos.UserDao
import com.olidev.synapse_mobile.data.local.entities.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    fun getUserById(userId : String) : Flow<User?> = userDao.getUser(userId)

    suspend fun upsertUser(user: User){
        userDao.insertUser(user)
    }

    suspend fun removeUser(userId: String){
        userDao.deleteUser(userId)
    }

}