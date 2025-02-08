package com.kinected.myapplication.network

import com.kinected.myapplication.data.CountryResponseItem
import retrofit2.http.GET

interface ApiService{

    @GET("/DevTides/countries/master/countriesV2.json")
    suspend fun getCountryList(): List<CountryResponseItem>
}
