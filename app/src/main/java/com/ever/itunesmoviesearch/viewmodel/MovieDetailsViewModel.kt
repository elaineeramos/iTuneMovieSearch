package com.ever.itunesmoviesearch.viewmodel

import androidx.lifecycle.ViewModel
import com.ever.itunesmoviesearch.model.MovieRepository
import com.ever.itunesmoviesearch.model.moviedata.MovieDescription
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable

/**
 * ViewModel Class for Movie Detail
 * Responsible for streaming data to the view and
 * asking data from the repository
 *
 * ViewModel class is chosen so that UI related data
 * is ensured to stay during the life cycle of the viewmodel
 * and survives device configuration change
 */
class MovieDetailsViewModel : ViewModel() {

    private val movieRepository = MovieRepository.shared

//    val movieDescription = BehaviorRelay.createDefault(listOf<MovieDescription>())

    private val bag = CompositeDisposable()

    init {
        println("Start movie details view model")

    }

    /**
     * Obtain the full description of a selected movie from repository
     *
     * @param None
     * @return movie description
     */
    fun getMovieFullDetail(movieIndex : Int): Flowable<MovieDescription> {
        return movieRepository.getMovieFullDescription(movieIndex)
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