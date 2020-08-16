package com.example.rxpractice.network

import com.example.rxpractice.model.Contact
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServer {

    @GET("contacts.php")
    fun getContacts(@Query("source") source: String, @Query("search") query: String): Single<List<Contact>>
}
