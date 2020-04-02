package com.ever.itunesmoviesearch.model

import androidx.room.*
import com.ever.itunesmoviesearch.MovieSearchApplication
import com.ever.itunesmoviesearch.model.moviedata.ItunesResponse
import com.ever.itunesmoviesearch.model.moviedata.MovieDescription
import com.ever.itunesmoviesearch.model.moviedata.MovieDetailDao
import com.ever.itunesmoviesearch.utility.getCurrentTime
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 *
 * Repository for Movie Descriptions
 *
 */
class MovieRepository {

    companion object {
        val shared = MovieRepository()
    }

    /**
     *
     * Set the obtained response from network to the repository
     *
     * @param response response from the iTunes query
     * @return None
     */
    fun setMovieToDatabase (response: ItunesResponse?) {
        val movieDescriptionList = convertResponseToDbData(response)

        GlobalScope.launch{
            val database =
                MovieSearchApplication.database
            database.movieDetailDao().insertMovieDescriptions(movieDescriptionList)
        }
    }

    /**
     *
     * Set the response and current time to the MovieDescription
     *
     * @param response response from the iTunes query
     * @return list of movie description
     */
    private fun convertResponseToDbData (response: ItunesResponse?) : ArrayList<MovieDescription> {
        val movieDbList: ArrayList<MovieDescription> = arrayListOf()
        val time = getCurrentTime()
        var movieIndex = 0

        response?.results?.forEach{
            val movie = MovieDescription(
                movieIndex,
                it.trackName,
                it.artworkUrl100,
                "A$" + it.trackPrice.toString(),
                it.primaryGenreName,
                it.longDescription,
                time,
                ""
            )

            movieDbList.add(movie)
            movieIndex++
        }

        return movieDbList
    }

    /**
     *
     * Obtain the number of data in repository
     *
     */
    fun getMovieDescriptionCount() = MovieSearchApplication.database.movieDetailDao().getMovieDescriptionCount()

    /**
     *
     * Obtain all the movie descriptions in the repository
     *
     */
    fun getAllMovieDescription() = MovieSearchApplication.database.movieDetailDao().getAllMovieDescriptions()

    /**
     *
     * Obtain movie description for specified movie index
     *
     */
    fun getMovieFullDescription(movieIndex: Int) = MovieSearchApplication.database.movieDetailDao().getMovieFullDescription(movieIndex)

    /**
     *
     * Set the last access date for a movie description
     *
     */
    fun updateAccessDate(accessTime: String, movieIndex: Int) = MovieSearchApplication.database.movieDetailDao().updateAccessDate(accessTime, movieIndex)

    fun removeAllMovies() = MovieSearchApplication.database.movieDetailDao().removeAllMovies()
}

/**
 *
 * Room database for the movie description
 *
 */
@Database(entities = [MovieDescription::class], version = 1, exportSchema = false)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun movieDetailDao(): MovieDetailDao
}