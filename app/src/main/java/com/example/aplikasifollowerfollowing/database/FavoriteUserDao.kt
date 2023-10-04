package com.example.aplikasifollowerfollowing.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM FavoriteUser")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUserEntitity>>

    @Query("SELECT EXISTS(SELECT * FROM FavoriteUser WHERE username = :username)")
    fun isFavorite(username: String): LiveData<Boolean>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteUser : FavoriteUserEntitity)

    @Delete
    suspend fun delete(favoriteUser: FavoriteUserEntitity)
}