package com.example.aplikasifollowerfollowing.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasifollowerfollowing.database.FavoriteUserEntitity
import com.example.aplikasifollowerfollowing.repository.UserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel (private val mRepository: UserRepository): ViewModel() {

    fun isFavorite(username: String) = mRepository.isFavorite(username)

    fun saveFavorite(favoriteUserEntitity: FavoriteUserEntitity){
        viewModelScope.launch { mRepository.saveFavorite(favoriteUserEntitity) }
    }

    fun deleteFavorite(favoriteUserEntitity: FavoriteUserEntitity) {
        viewModelScope.launch { mRepository.delete(favoriteUserEntitity) }
    }

    fun getAllUser(): LiveData<List<FavoriteUserEntitity>> {
        return mRepository.getFavoriteUser()
    }

}