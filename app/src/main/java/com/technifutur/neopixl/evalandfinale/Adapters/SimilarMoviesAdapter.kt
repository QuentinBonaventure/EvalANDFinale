package com.technifutur.neopixl.evalandfinale.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.technifutur.neopixl.evalandfinale.Model.Movie
import com.technifutur.neopixl.evalandfinale.databinding.SimilarMoviesCellBinding

class SimilarMoviesAdapter(private val movies: List<Movie>, val listener: ClickListener): RecyclerView.Adapter<SimilarMoviesAdapter.MoviesRowHolder>() {

    private lateinit var binding: SimilarMoviesCellBinding
    interface ClickListener{
        fun clickListener(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesRowHolder {
        binding = SimilarMoviesCellBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return MoviesRowHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesRowHolder, position: Int) {
        holder.bind(movies[position], listener)
    }

    override fun getItemCount(): Int {
      return movies.count()
    }

    class MoviesRowHolder(var binding: SimilarMoviesCellBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, listener: ClickListener){
            binding.movieTitle.text = movie.title

            if (!movie.posterPath.isNullOrEmpty()) {
                val imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
                Picasso.get()
                    .load(imageUrl)
                    .into(binding.movieImage)
            }

            itemView.setOnClickListener {
                listener.clickListener(movie)
            }




        }
    }



}