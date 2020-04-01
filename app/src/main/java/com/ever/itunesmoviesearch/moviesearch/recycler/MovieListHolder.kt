package com.ever.itunesmoviesearch.moviesearch.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ever.itunesmoviesearch.R
import com.ever.itunesmoviesearch.model.moviedata.MovieDescription

/**
 * Class responsible for movie list view holder
 *
 */
class MovieListHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    val context = itemView.context
    val imageView : ImageView
    val movieTitleTextView: TextView
    val genreTextView: TextView
    val priceTextView: TextView
    val accessTimeTextView: TextView

    init {
        imageView = itemView.findViewById(R.id.moviePoster)
        movieTitleTextView = itemView.findViewById(R.id.movieTitleTextView)
        genreTextView = itemView.findViewById(R.id.genreTextView)
        priceTextView = itemView.findViewById(R.id.priceTextView)
        accessTimeTextView = itemView.findViewById(R.id.accessTimeTextView)
    }

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
        movieTitleTextView.text = movieDescription.trackName
        genreTextView.text = movieDescription.genre
        priceTextView.text = context.getString(R.string.aud_sign) + movieDescription.price
        accessTimeTextView.text = movieDescription.accessDate
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
        Glide.with(context)
             .load(movieDescription.artwork)
             .placeholder(R.drawable.ic_movie_roll)
             .error(R.drawable.ic_movie_roll)
             .dontAnimate()
             .into(imageView)
    }
}