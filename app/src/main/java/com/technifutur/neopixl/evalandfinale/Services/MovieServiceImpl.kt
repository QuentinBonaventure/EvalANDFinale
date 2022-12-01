package com.technifutur.neopixl.evalandfinale.Services

import com.technifutur.neopixl.evalandfinale.BASE_URL
import com.technifutur.neopixl.evalandfinale.Model.Movie
import com.technifutur.neopixl.evalandfinale.Model.MovieModel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MovieServiceImpl: MovieService {
    private fun getRetrofit(): Retrofit {
        val okBuilder = OkHttpClient().newBuilder().apply {
            connectTimeout(120, TimeUnit.SECONDS)
            callTimeout(120,TimeUnit.SECONDS)
            readTimeout(120,TimeUnit.SECONDS)
            writeTimeout(120,TimeUnit.SECONDS)
        }
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okBuilder.build())
            .build()

    }

    override suspend fun movie(id: Int, apiKey: String, language: String): Response<Movie> {
        return getRetrofit().create(MovieService::class.java).movie(id)
    }

    override suspend fun assets(
        query: String,
        apiKey: String,
        includeAdult: Boolean,
        language: String
    ): Response<MovieModel> {
        return getRetrofit().create(MovieService::class.java).assets(query)
    }

    override suspend fun trendingMovies(apiKey: String): Response<MovieModel> {
        return getRetrofit().create(MovieService::class.java).trendingMovies()
    }
    override suspend fun similarMovies(id: Int, apiKey: String, language: String): Response<MovieModel> {
        return getRetrofit().create(MovieService::class.java).similarMovies(id)
    }
}