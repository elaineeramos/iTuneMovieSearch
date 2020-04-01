package com.ever.itunesmoviesearch.viewmodel

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.ever.itunesmoviesearch.R
import com.ever.itunesmoviesearch.model.MovieRepository
import com.ever.itunesmoviesearch.model.moviedata.ItunesResponse
import com.ever.itunesmoviesearch.model.moviedata.MovieDescription
import com.ever.itunesmoviesearch.model.network.Network
import com.ever.itunesmoviesearch.moviesearch.recycler.MovieListAdapter
import com.ever.itunesmoviesearch.utility.getCurrentTime
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

/**
 * ViewModel Class for Movie Search
 * Responsible for streaming data to the view and
 * asking data from the repository
 *
 * ViewModel class is chosen so that UI related data
 * is ensured to stay during the life cycle of the viewmodel
 * and survives device configuration change
 */
class MovieSearchViewModel : ViewModel() {
    lateinit var adapter: MovieListAdapter

    private val network = Network.instance
    private val movieRepository = MovieRepository.shared

    private val bag = CompositeDisposable()

    init {
        println("Start Movie Search view model")
    }

    /**
     * Obtain the iTunes response from network
     *
     * @param None
     * @return iTunes Response
     */
    fun getMessagesRx(): Single<ItunesResponse> {
        return network.getMessagesRx()
    }

    /**
     * Set the movie to database
     *
     * @param response response from iTunes
     * @return iTunes Response
     */
    fun setMovieToDatabase(movieDetails: ItunesResponse) {
        movieRepository.setMovieToDatabase(movieDetails)
    }

    /**
     * Obtain the description of all movies
     *
     * @param None
     * @return list of movie descriptions
     */
    fun getMovieDetail(): Flowable<List<MovieDescription>> {
        return movieRepository.getAllMovieDescription()
    }

    /**
     * Obtain the number of movie description in the repository
     *
     * @param None
     * @return number of movie description
     */
    fun getMovieDescriptionCount(): Int {
        return movieRepository.getMovieDescriptionCount()
    }

    /**
     * Update the access time of the movie description selected
     *
     * @param movieIndex index of movie description selected
     * @return None
     */
    fun updateMovieDescriptionAccessDate(movieIndex : Int) {
        val displayText = "Data access time: " + getCurrentTime()
        movieRepository.updateAccessDate(displayText, movieIndex)
    }

    fun removeAllMovies() {
        movieRepository.removeAllMovies()
    }

    /**
     * Clear and dispose all the contained Disposables in the application
     * when movie search activity is destroyed
     *
     * @param None
     * @return None
     */
    override fun onCleared() {
        super.onCleared()
        bag.clear()
    }
}