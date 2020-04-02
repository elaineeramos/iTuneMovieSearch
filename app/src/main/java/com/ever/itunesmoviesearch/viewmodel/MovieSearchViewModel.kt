package com.ever.itunesmoviesearch.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ever.itunesmoviesearch.model.MovieRepository
import com.ever.itunesmoviesearch.model.moviedata.ItunesResponse
import com.ever.itunesmoviesearch.model.moviedata.MovieDescription
import com.ever.itunesmoviesearch.model.network.Network
import com.ever.itunesmoviesearch.moviesearch.recycler.MovieListAdapter
import com.ever.itunesmoviesearch.utility.getCurrentTime
import com.ever.itunesmoviesearch.utility.isNetworkAvailable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

/**
 * ViewModel Class for Movie Search
 * Responsible for streaming data to the view and
 * asking data from the repository
 *
 * ViewModel class is chosen so that UI related data
 * is ensured to stay during the life cycle of the view model
 * and survives device configuration change
 */
class MovieSearchViewModel : ViewModel() {
    lateinit var adapter: MovieListAdapter

    private val network = Network.instance
    private val movieRepository = MovieRepository.shared

    private val bag = CompositeDisposable()

    var snackbarNotificationData = MutableLiveData<Boolean>()

    /**
     * Obtain the iTunes response from network
     *
     * @return iTunes Response
     */
    fun getMessagesRx(): Single<ItunesResponse> {
        return network.getMessagesRx()
    }

    /**
     * Set the movie to database
     *
     * @param movieDetails iTunes Response of movie details
     */
    fun setMovieToDatabase(movieDetails: ItunesResponse) {
        movieRepository.setMovieToDatabase(movieDetails)
    }

    /**
     * Obtain the description of all movies
     *
     * @return list of movie descriptions
     */
    fun getMovieDetail(): Flowable<List<MovieDescription>> {
        return movieRepository.getAllMovieDescription()
    }

    /**
     * Obtain the number of movie description in the repository
     *
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

    /**
     * Remove all movies in the database
     *
     */
    fun removeAllMovies() {
        movieRepository.removeAllMovies()
    }

    /**
     * Set snackbar event to false
     *
     */
    fun isDeviceConnectedToNetwork(context: Context) : Boolean {
        val isNetworkAvailable = isNetworkAvailable(context)
        snackbarNotificationData.postValue(!isNetworkAvailable)
        return isNetworkAvailable
    }

    /**
     * Set snackbar event to false
     *
     */
    fun doneShowingSnackbar() {
        snackbarNotificationData.value = false
    }

    /**
     * Clear and dispose all the contained Disposables in the application
     * when movie search activity is destroyed
     *
     */
    override fun onCleared() {
        super.onCleared()
        bag.clear()
    }
}