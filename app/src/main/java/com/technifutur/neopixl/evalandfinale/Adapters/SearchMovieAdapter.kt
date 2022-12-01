package com.technifutur.neopixl.evalandfinale.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.technifutur.neopixl.evalandfinale.Model.Movie
import com.technifutur.neopixl.evalandfinale.databinding.SearchMovieCellBinding

class SearchMovieAdapter(private val movies: List<Movie>, val listener: ClickListener) : RecyclerView.Adapter<SearchMovieAdapter.MovieRowHolder>() {

    private lateinit var binding: SearchMovieCellBinding
    interface ClickListener{
        fun clickListener(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRowHolder {
        binding = SearchMovieCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieRowHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieRowHolder, position: Int) {
        holder.bind(movies[position], listener)
    }

    override fun getItemCount(): Int {
       return movies.count()
    }

    class MovieRowHolder(var binding: SearchMovieCellBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(movie: Movie, listener: ClickListener){
            binding.movieTitle.text = movie.title
            binding.movieReleaseDate.text = movie.releaseDate
            binding.movieRating.text = movie.voteAverage.toString()

            if(!movie.posterPath.isNullOrEmpty()){
                val imageURL = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
                Picasso.get()
                    .load(imageURL)
                    .into(binding.movieImg)
            }
            itemView.setOnClickListener {
                listener.clickListener(movie)
            }
        }
    }


}