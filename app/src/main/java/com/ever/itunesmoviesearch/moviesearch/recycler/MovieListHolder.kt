package com.ever.itunesmoviesearch.moviesearch.recycler

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ever.itunesmoviesearch.R
import com.ever.itunesmoviesearch.databinding.ItemMovieOverviewBinding
import com.ever.itunesmoviesearch.model.moviedata.MovieDescription

/**
 * Class responsible for movie list view holder
 * Use data binding to set data on movie list overview
 *
 */
class MovieListHolder (private var binding: ItemMovieOverviewBinding, private var context: Context) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Set the movie details on the text and image views in the holder
     *
     * @param movieDescription movie details from the database
     * @return None
     */
    fun setMovieDetail(movieDescription: MovieDescription) {
        setMovieTextDetail(movieDescription)
        setMoviePoster(movieDescription)
    }

    /**
     * Set the text view to correct movie details
     *
     * @param movieDescription movie details from the database
     * @return None
     */
    private fun setMovieTextDetail(movieDescription: MovieDescription) {
        this.binding.movieOverview = movieDescription
    }

    /**
     * Set image view on movie list holder by using Glide
     * Image is obtained by using the URL provided by iTunes
     * When image is not yet fully downloaded, placeholder image is used
     *
     * @param movieDescription movie details from the database
     * @return None
     */
    private fun setMoviePoster(movieDescription: MovieDescription) {
        Glide.with(context.applicationContext)
             .load(movieDescription.artwork)
             .placeholder(R.drawable.ic_movie_roll)
             .error(R.drawable.ic_movie_roll)
             .dontAnimate()
             .into(binding.moviePoster)
    }
}