package com.ever.itunesmoviesearch.model.moviedata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

/**
 * Data Access Object for the movie descriptions
 */
@Dao
interface MovieDetailDao {

    /**
     * Obtain all the movies from the database
     *
     * @return list of containing all movie description from the database
     */
    @Query("SELECT * FROM movie_descriptions LIMIT 100")
    fun getAllMovieDescriptions(): Flowable<List<MovieDescription>>

    /**
     * Obtain specific movie description from the database
     *
     * @param movieIndex position of the movie description in the database
     * @return movie description for specific item in the database
     */
    @Query("SELECT * FROM movie_descriptions WHERE list_position = :movieIndex ")
    fun getMovieFullDescription(movieIndex: Int): Flowable<MovieDescription>

    /**
     * Obtains the number of movie description entries in the database
     *
     * @return number of movie description in the database
     */
    @Query("SELECT COUNT (*) from movie_descriptions")
    fun getMovieDescriptionCount() : Int

    /**
     * Set movie description to the database
     * When primary key exists in database, old data is replaced
     *
     * @param movies movie description obtained from iTunes
     * @return None
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDescriptions(movies: List<MovieDescription>)

    /**
     * Updating only amount and price
     * By order id
     */
    @Query("UPDATE movie_descriptions SET access_date = :access_date WHERE list_position =:position")
    fun updateAccessDate(access_date: String, position: Int)

    /**
     * Removes all entries in the database
     *
     * @return None
     */
    @Query("DELETE FROM movie_descriptions")
    fun removeAllMovies()
}