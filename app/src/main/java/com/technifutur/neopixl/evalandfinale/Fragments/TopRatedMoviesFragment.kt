package com.technifutur.neopixl.evalandfinale.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technifutur.neopixl.evalandfinale.Adapters.TopRatedMovieAdapter
import com.technifutur.neopixl.evalandfinale.Model.Movie
import com.technifutur.neopixl.evalandfinale.R
import com.technifutur.neopixl.evalandfinale.Services.MovieServiceImpl
import com.technifutur.neopixl.evalandfinale.databinding.FragmentTopRatedMoviesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TopRatedMoviesFragment : Fragment(), TopRatedMovieAdapter.ClickListener {
  private var binding: FragmentTopRatedMoviesBinding?= null
    private val movieService by lazy { MovieServiceImpl()}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopRatedMoviesBinding.inflate(layoutInflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trending()
    }

    private fun setupRecyclerView(data: List<Movie>) {
        val recyclerView = binding?.topRatedRecyclerView
        recyclerView?.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
        recyclerView?.adapter = TopRatedMovieAdapter(data, this)
    }

    override fun clickListener(movie: Movie) {
        findNavController().navigate(TopRatedMoviesFragmentDirections.actionTopRatedMoviesFragmentToDetailMovieFragment(movie.id))
    }
    fun trending() {

        CoroutineScope(Dispatchers.IO).launch {
            val response = movieService.trendingMovies()
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    response.body()?.results?.let {
                        setupRecyclerView(it)
                    }
                }
            }

        }

    }
}