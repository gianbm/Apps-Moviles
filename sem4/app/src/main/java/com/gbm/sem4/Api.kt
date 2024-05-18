package com.gbm.sem4

import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("users")
    fun obtenerUsuarios(): Call<List<Usuario>>

}