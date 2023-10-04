package com.example.aplikasifollowerfollowing.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "FavoriteUser")
@Parcelize
data class FavoriteUserEntitity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    var username: String = "",

    @ColumnInfo(name = "avataruser")
    var avataruser: String? = null

):Parcelable
