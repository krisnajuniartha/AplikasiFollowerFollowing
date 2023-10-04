package com.example.aplikasifollowerfollowing.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized


@Database(entities = [FavoriteUserEntitity::class], version = 1, exportSchema = false)
abstract class FavoriteUserDatabase : RoomDatabase(){

    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var instance: FavoriteUserDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        @JvmStatic
        fun getDatabase(context: Context): FavoriteUserDatabase {
            if (instance == null) {
                synchronized(FavoriteUserDatabase::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteUserDatabase ::class.java, "favorite_user_database"
                    )
                        .build()
                }
            }
            return instance as FavoriteUserDatabase
        }

    }
}
