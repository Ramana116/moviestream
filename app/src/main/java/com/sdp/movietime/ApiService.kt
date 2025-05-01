package com.sdp.movietime

import com.sdp.movietime.DataClass.LoginResponse
import com.sdp.movietime.DataClass.UserLoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("/auth/register")
    fun registerUser(@Body user: User): Call<User>

    @POST("/auth/login")
    fun loginUser(@Body user: UserLoginRequest): Call<LoginResponse>

    @DELETE("auth/delete/{username}")
    fun deleteUser(@Path("username") username: String): Call<Void>

    // In RetrofitClient
    // RetrofitClient - Update Username
    @PUT("/auth/update-username")
    fun updateUsername(@Body requestBody: Map<String, String>): Call<Void>

}