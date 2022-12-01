package com.technifutur.neopixl.evalandfinale.Services

import com.technifutur.neopixl.evalandfinale.Model.Movie
import com.technifutur.neopixl.evalandfinale.Model.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @Headers("Content-type: application/json")
    @GET("https://api.themoviedb.org/3/search/movie?api_key=1289bdc96c0e3bf709e4789f7a01faf9&language=en-US&page=1&include_adult=false&")
    suspend fun assets(@Query("query") query: String): Response<MovieModel>

    @Headers("Content-type: application/json")
    @GET("https://api.themoviedb.org/3/movie/{id}?api_key=1289bdc96c0e3bf709e4789f7a01faf9&language=en-US")
    suspend fun movie(@Path("id") id: Int ): Response<Movie>

    @Headers("Content-type: application/json")
    @GET("https://api.themoviedb.org/3/movie/{id}/similar?api_key=1289bdc96c0e3bf709e4789f7a01faf9&language=en-US")
    suspend fun similarMovies(@Path("id") id: Int): Response<MovieModel>

    @Headers("Content-type: application/json")
    @GET("https://api.themoviedb.org/3/trending/all/day?api_key=1289bdc96c0e3bf709e4789f7a01faf9")
    suspend fun trendingMovies(): Response<MovieModel>
}