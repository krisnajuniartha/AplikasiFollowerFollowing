package com.example.aplikasifollowerfollowing.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasifollowerfollowing.di.injection
import com.example.aplikasifollowerfollowing.repository.UserRepository
import com.example.aplikasifollowerfollowing.ui.favorite.FavoriteViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> return FavoriteViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: "+ modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {

                instance ?: ViewModelFactory(injection.provideRepository(context))
            }.also { instance = it }
    }

}