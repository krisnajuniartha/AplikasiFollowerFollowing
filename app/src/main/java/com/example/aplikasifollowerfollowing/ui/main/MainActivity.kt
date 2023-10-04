package com.example.aplikasifollowerfollowing.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasifollowerfollowing.R
import com.example.aplikasifollowerfollowing.adapter.UsersAdapter
import com.example.aplikasifollowerfollowing.data.ItemsItem
import com.example.aplikasifollowerfollowing.databinding.ActivityMainBinding
import com.example.aplikasifollowerfollowing.factory.ThemeFactory
import com.example.aplikasifollowerfollowing.ui.favorite.FavoriteActivity
import com.example.aplikasifollowerfollowing.ui.theme.ThemeActivity
import com.example.aplikasifollowerfollowing.ui.theme.ThemePreference
import com.example.aplikasifollowerfollowing.ui.theme.ThemeViewModel




class MainActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Settings")

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.text = searchView.text
                mainViewModel.findUser(searchView.text.toString())
                searchView.hide()
                showLoading(true)
                false

            }
        }


        mainViewModel.listUser.observe(this){ user ->
            setUserData(user)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvProfile.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvProfile.addItemDecoration(itemDecoration)

        binding.topAppBar.setOnMenuItemClickListener {menuItem ->
            when(menuItem.itemId) {
                R.id.favTop -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.themeTop -> {
                    val themeintent = Intent(this, ThemeActivity::class.java)
                    startActivity(themeintent)
                    true
                }
                else -> false
            }
        }

        //di observe agar saat menutup aplikasi tidak kembali ke sttingan awal
        val preference = ThemePreference.getInstance(application.dataStore)

        val themeViewModel = ViewModelProvider(this, ThemeFactory(preference)).get(
            ThemeViewModel::class.java
        )

        themeViewModel.getThemeSettings().observe(this) {
            isActive(it)
        }


    }

    private fun setUserData (user: List<ItemsItem>){
        val adapter = UsersAdapter(this)
        adapter.submitList(user)
        binding.rvProfile.adapter = adapter
    }

    private fun isActive(isDarkModeActive: Boolean) {
        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}