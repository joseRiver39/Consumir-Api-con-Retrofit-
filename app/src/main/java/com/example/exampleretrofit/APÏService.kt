package com.example.exampleretrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APÏService {
    @GET
     suspend fun  getDoogsByBreeds(@Url url:String):Response<DogResponse>
}