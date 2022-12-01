package com.technifutur.neopixl.evalandfinale.Services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseService {

     val BASE_URL = "https://api.themoviedb.org/3/"
     val API_KEY = "b2168bae3a2c67509eb6b97572f521c2"

     val retrofit = Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build()
}