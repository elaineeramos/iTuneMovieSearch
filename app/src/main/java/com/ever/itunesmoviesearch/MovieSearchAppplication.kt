package com.ever.itunesmoviesearch

import android.app.Application
import androidx.room.Room
import com.ever.itunesmoviesearch.model.LocalDatabase

/**
 * Builds the database
 */
class MovieSearchApplication: Application() {

    companion object {
        lateinit var database: LocalDatabase
    }

    override fun onCreate() {
        super.onCreate()

        setupDatabase()
    }

    /**
     * Setup room using database builder so that information in
     * the database persists even if after application is restarted
     *
     * @return None
     */
    private fun setupDatabase(){
        database = Room.databaseBuilder(
            this,
            LocalDatabase::class.java, "MovieSearchLocalDatabase"
        ).build()
    }
}