package com.ever.itunesmoviesearch.model.moviedata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity for the movie description stored in database
 */
@Entity(tableName = "movie_descriptions")
data class MovieDescription(
    @PrimaryKey
    @ColumnInfo(name = "list_position") var listPosition: Int,
    @ColumnInfo(name = "track_name") var trackName: String,
    @ColumnInfo(name = "artwork") var artwork: String,
    @ColumnInfo(name = "price") var price: String,
    @ColumnInfo(name = "genre") var genre: String,
    @ColumnInfo(name = "long_description") var longDescription: String,
    @ColumnInfo(name = "last_retrieved") var lastRetrieved: String,
    @ColumnInfo(name = "access_date") var accessDate: String
)