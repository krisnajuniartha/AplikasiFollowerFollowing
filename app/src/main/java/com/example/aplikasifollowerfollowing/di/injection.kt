package com.example.aplikasifollowerfollowing.di

import android.content.Context
import com.example.aplikasifollowerfollowing.database.FavoriteUserDatabase
import com.example.aplikasifollowerfollowing.repository.UserRepository

object injection {
    fun provideRepository(context: Context): UserRepository {
        val database = FavoriteUserDatabase.getDatabase(context)
        val dao = database.favoriteUserDao()

        return UserRepository.getInstance(dao)
    }
}