package com.technifutur.neopixl.evalandfinale.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.technifutur.neopixl.evalandfinale.Adapters.SimilarMoviesAdapter
import com.technifutur.neopixl.evalandfinale.Model.Movie
import com.technifutur.neopixl.evalandfinale.R
import com.technifutur.neopixl.evalandfinale.Services.MovieService
import com.technifutur.neopixl.evalandfinale.Services.MovieServiceImpl
import com.technifutur.neopixl.evalandfinale.databinding.FragmentDetailMovieBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class DetailMovieFragment : Fragment(), SimilarMoviesAdapter.ClickListener {

private var binding : FragmentDetailMovieBinding? = null
    private val movieService by lazy { MovieServiceImpl() }
    private val args: DetailMovieFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getSimilarMovies(args.movieId)
        getMovie(args.movieId)
        binding = FragmentDetailMovieBinding.inflate(layoutInflater, container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.backButton?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpRecyclerView(model: List<Movie>){
        val recyclerView = binding?.similarMovieRecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView?.adapter = SimilarMoviesAdapter(model, this)
    }

    private fun getSimilarMovies(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieService.similarMovies(id)
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    response.body()?.results?.let {
                        setUpRecyclerView(it)
                    }
                }
            }
        }
    }

    private fun getMovie(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieService.movie(id)
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    response.body()?.let {
                        binding?.movieTitle?.text = it.title
                        binding?.movieRating?.text = it.voteAverage.toString().subSequence(0..2)
                        binding?.movieSynopsis?.text = it.overview

                        if (!it.posterPath.isNullOrEmpty()) {
                            val imageUrl = "https://image.tmdb.org/t/p/w500${it.posterPath}"
                            Picasso.get()
                                .load(imageUrl)
                                .into(binding?.movieImage)
                        }

                        if (!it.backdropPath.isNullOrEmpty()) {
                            val imageUrl = "https://image.tmdb.org/t/p/w500${it.backdropPath}"
                            Picasso.get()
                                .load(imageUrl)
                                .into(binding?.movieBackImage)
                        }
                    }
                }
            }
        }
    }

    override fun clickListener(movie: Movie) {
        getMovie(movie.id)
        getSimilarMovies(movie.id)
    }


}