package com.technifutur.neopixl.evalandfinale.Services

import com.technifutur.neopixl.evalandfinale.API_KEY
import com.technifutur.neopixl.evalandfinale.LANGUAGE
import com.technifutur.neopixl.evalandfinale.Model.Movie
import com.technifutur.neopixl.evalandfinale.Model.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @Headers("Content-type: application/json")
    @GET("movie/{id}")
    suspend fun movie(@Path("id") id: Int,
                      @Query("api_key", encoded = false) apiKey: String = API_KEY,
                      @Query("language", encoded = false) language: String = LANGUAGE
    ): Response<Movie>

    @Headers("Content-type: application/json")
    @GET("search/movie")
    suspend fun assets(@Query("query") query: String,
                       @Query("api_key", encoded = false) apiKey: String = API_KEY,
                       @Query("include_adult", encoded = false) includeAdult: Boolean = false,
                       @Query("language", encoded = false) language: String = LANGUAGE

    ): Response<MovieModel>

    @Headers("Content-type: application/json")
    @GET("trending/all/day")
    suspend fun trendingMovies(@Query("api_key", encoded = false) apiKey: String = API_KEY): Response<MovieModel>

    @Headers("Content-type: application/json")
    @GET("movie/{id}/similar")
    suspend fun similarMovies(@Path("id") id: Int,
                              @Query("api_key", encoded = false) apiKey: String = API_KEY,
                              @Query("language", encoded = false) language: String = LANGUAGE
    ): Response<MovieModel>
}