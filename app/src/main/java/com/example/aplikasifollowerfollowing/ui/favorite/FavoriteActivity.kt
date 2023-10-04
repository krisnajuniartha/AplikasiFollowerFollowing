package com.example.aplikasifollowerfollowing.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasifollowerfollowing.adapter.FavoriteAdapter
import com.example.aplikasifollowerfollowing.databinding.ActivityFavoriteBinding
import com.example.aplikasifollowerfollowing.factory.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModelFavorite = obtainViewModel(this@FavoriteActivity)
        mainViewModelFavorite.getAllUser().observe(this) {userFavorite ->
            if (userFavorite != null){
                adapter.setListUser(userFavorite)
            }
        }

        adapter = FavoriteAdapter()
        binding.rvFavoriteUser.layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteUser.setHasFixedSize(true)
        binding.rvFavoriteUser.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }


}