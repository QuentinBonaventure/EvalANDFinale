package com.technifutur.neopixl.evalandfinale.Services

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
            connectTimeout(60, TimeUnit.SECONDS)
            callTimeout(60, TimeUnit.SECONDS)
            readTimeout (60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
        }
        return Retrofit.Builder()
            .baseUrl("https://apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okBuilder.build())
            .build()
    }
    override suspend fun assets(query: String): Response<MovieModel> {

        var test = getRetrofit().create(MovieService::class.java).assets(query)
        return test
    }

    override suspend fun movie(id: Int): Response<Movie> {
        return getRetrofit().create(MovieService::class.java).movie(id)
    }

    override suspend fun similarMovies(id: Int): Response<MovieModel> {
        return getRetrofit().create(MovieService::class.java).similarMovies(id)
    }

    override suspend fun trendingMovies(): Response<MovieModel> {
        return getRetrofit().create(MovieService::class.java).trendingMovies()
    }
}