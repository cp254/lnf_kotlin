package io.ginius.cp.kt.lostfound.kotlin.api

import io.ginius.cp.kt.lostfound.kotlin.App
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiService {





    @POST("mobile/v1/")
    @Headers("Content-Type: application/json")
    fun reg(@Body data: io.ginius.cp.kt.lostfound.kotlin.api.post.Register.Request): Observable<io.ginius.cp.kt.lostfound.kotlin.api.post.Register.Response>

    @POST("mobile/v1/")
    @Headers("Content-Type: application/json")
    fun searchDoc(@Body data: io.ginius.cp.kt.lostfound.kotlin.api.post.SearchDoc.Request): Observable<io.ginius.cp.kt.lostfound.kotlin.api.post.SearchDoc.Response>


    companion object {
        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(App.API)
                    .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}