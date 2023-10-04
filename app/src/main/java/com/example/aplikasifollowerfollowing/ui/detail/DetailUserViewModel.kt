package com.example.aplikasifollowerfollowing.ui.detail

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasifollowerfollowing.data.DetailUserResponse
import com.example.aplikasifollowerfollowing.data.ItemsItem
import com.example.aplikasifollowerfollowing.database.FavoriteUserEntitity
import com.example.aplikasifollowerfollowing.repository.UserRepository
import com.example.aplikasifollowerfollowing.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel (application: Application): AndroidViewModel(application) {


    private val _user = MutableLiveData<DetailUserResponse>()
    val users: LiveData<DetailUserResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFollower = MutableLiveData<List<ItemsItem>>()
    val isFollower: LiveData<List<ItemsItem>> = _isFollower

    private val _isFollowing = MutableLiveData<List<ItemsItem>>()
    val isFollowing: LiveData<List<ItemsItem>> = _isFollowing

    private val _snackbarText = MutableLiveData<String>()
    val snackbarText: LiveData<String> = _snackbarText


    companion object {
        private const val TAG = "UserDetailViewModel"
    }

    fun getUserDetail(username : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {

                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollower(follower: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(follower)
        client.enqueue(object : Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isFollower.value = response.body()
//   
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getFollowing(following: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(following)
        client.enqueue(object : Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isFollowing.value = response.body()
//                    _snackbarText.value = "Anda Berhasil"
//                    Log.d("findUser", "onCreate: ${response.body()}")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }



}