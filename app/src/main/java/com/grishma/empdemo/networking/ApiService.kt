package com.grishma.empdemo.networking

import com.grishma.empdemo.model.Employee
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Api Service class
 */
interface ApiService {

    @GET("employees")
    fun getEmployees(): Call<Employee>


    companion object {
        private const val BASE_URL = "http://dummy.restapiexample.com/api/v1/"

        fun createService(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
