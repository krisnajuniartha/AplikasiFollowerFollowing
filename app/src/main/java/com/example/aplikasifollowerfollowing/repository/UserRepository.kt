package com.example.aplikasifollowerfollowing.repository

import androidx.lifecycle.LiveData
import com.example.aplikasifollowerfollowing.database.FavoriteUserDao
import com.example.aplikasifollowerfollowing.database.FavoriteUserEntitity
import com.example.aplikasifollowerfollowing.retrofit.ApiService

class UserRepository(
    private val userdao: FavoriteUserDao,

) {

    suspend fun saveFavorite(favoriteUserEntitity: FavoriteUserEntitity) {
        userdao.insert(favoriteUserEntitity)
    }

    suspend fun delete(favoriteUserEntitity: FavoriteUserEntitity) {
        userdao.delete(favoriteUserEntitity)
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUserEntitity>> = userdao.getAllFavoriteUser()

    fun isFavorite(username: String): LiveData<Boolean> {
        return userdao.isFavorite(username)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            userdao: FavoriteUserDao
        ): UserRepository = instance?: synchronized(this){
            instance ?: UserRepository(userdao)
        }.also { instance = it }
    }

}