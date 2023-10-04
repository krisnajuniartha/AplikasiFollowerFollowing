package com.example.aplikasifollowerfollowing

import androidx.recyclerview.widget.DiffUtil
import com.example.aplikasifollowerfollowing.database.FavoriteUserEntitity

class FavoriteUserDiffCallback(private val oldFavoriteList: List<FavoriteUserEntitity>, private val newFavoriteList: List<FavoriteUserEntitity>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteList.size
    override fun getNewListSize(): Int = newFavoriteList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteList[oldItemPosition].username == newFavoriteList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldFavoriteList[oldItemPosition]
        val newNote = newFavoriteList[newItemPosition]
        return oldNote.avataruser == newNote.avataruser && oldNote.username == oldNote.username
    }
}