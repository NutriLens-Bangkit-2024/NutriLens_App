package com.capstone.nutrilens.data.api

import com.capstone.nutrilens.data.response.NewsListApiResponse
import com.capstone.nutrilens.data.response.CaloriesResponse
import com.capstone.nutrilens.data.response.EditUserRequest
import com.capstone.nutrilens.data.response.EditUserResponse
import com.capstone.nutrilens.data.response.LoginRequest
import com.capstone.nutrilens.data.response.LoginResponse
import com.capstone.nutrilens.data.response.ModelTestingResponse
import com.capstone.nutrilens.data.response.NewsDetailApiResponse
import com.capstone.nutrilens.data.response.PredictionResponse
import com.capstone.nutrilens.data.response.RecipeResponse
import com.capstone.nutrilens.data.response.RegisterResponse
import com.capstone.nutrilens.data.response.ScanningTestingResponse
import com.capstone.nutrilens.data.response.UserResponse
import com.capstone.nutrilens.ui.camera.model.ModelRequest
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiConfig {
    @FormUrlEncoded
    @POST("user")
    fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("profileurl") profileUrl: String
    ): Call<ResponseBody>

    @POST("user/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @GET("user/{id}")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<UserResponse>

    @PATCH("user/{id}")
    suspend fun editUser(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
        @Body editUserRequest: EditUserRequest
    ): Response<EditUserResponse>

    @POST("logout")
    fun logout(
        @Header("Authorization") authorization: String
    ): Call<ResponseBody>

    @GET("news")
    fun getAllNews(
        @Header("Authorization") authorization: String
    ): Call<NewsListApiResponse>

    @FormUrlEncoded
    @POST("saveFood")
    fun postPredict(
        @Header("Authorization") authorization: String,
        @Field("label") label: String,
        @Field("calories") calories: Int
    ): Call<ResponseBody>

    @GET("weekly-calories")
    fun getCalories(
        @Header("Authorization") authorization: String
    ): Call<CaloriesResponse>

    @GET("news/{id}")
    fun getNews(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    ): Call<NewsDetailApiResponse>

    @GET("recipes")
    suspend fun getRecipes(
        @Header("Authorization") token:String
    ): RecipeResponse

    @FormUrlEncoded
    @POST("user")
    suspend fun registerUser(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("profileurl") profileurl:String
    ): RegisterResponse

    @Multipart
    @POST("predict")
    suspend fun getScanningResult(
        @Header("Authorization") token:String,
        @Part file : MultipartBody.Part
    ): ScanningTestingResponse

    @POST("predict")
    suspend fun getModelResult(
        @Body request : ModelRequest
    ): ModelTestingResponse

    @FormUrlEncoded
    @POST("saveFood")
    suspend fun savePrediction(
        @Header("Authorization") token:String,
        @Field("label") label: String,
        @Field("calories") calories: Int,
    ): PredictionResponse
}