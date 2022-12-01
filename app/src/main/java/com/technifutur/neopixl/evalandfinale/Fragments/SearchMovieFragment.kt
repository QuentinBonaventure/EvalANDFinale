package com.technifutur.neopixl.evalandfinale.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technifutur.neopixl.evalandfinale.Adapters.SearchMovieAdapter
import com.technifutur.neopixl.evalandfinale.Model.Movie
import com.technifutur.neopixl.evalandfinale.R

import com.technifutur.neopixl.evalandfinale.Services.MovieServiceImpl
import com.technifutur.neopixl.evalandfinale.databinding.FragmentSearchMovieBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchMovieFragment : Fragment(), SearchMovieAdapter.ClickListener {

    private var binding: FragmentSearchMovieBinding? = null
    private val movieService by lazy {  MovieServiceImpl()}
    private var title = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchMovieBinding.inflate(layoutInflater, container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.searchTextField?.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                title = binding?.searchTextField?.query.toString()
                getAssets(title)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                title = binding?.searchTextField?.query.toString()
                getAssets(title)
                return true
            }
        })
    }
    fun getAssets(query: String?) {

        if(query.isNullOrEmpty()) {
            setupRecyclerView(listOf())
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val response = movieService.assets(query)
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
    private fun setupRecyclerView(data: List<Movie>) {
        val recyclerView = binding?.SearchMovieRecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView?.adapter = SearchMovieAdapter(data, this)

    }


    override fun clickListener(movie: Movie) {
        findNavController().navigate(SearchMovieFragmentDirections.actionSearchMovieFragmentToDetailMovieFragment(movie.id))
    }


}