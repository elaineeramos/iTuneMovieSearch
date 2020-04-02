package com.ever.itunesmoviesearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ever.itunesmoviesearch.moviesearch.MovieSearchActivity

/**
 *
 * This class starts the movie search activity
 *
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startMovieSearch()

        finish()
    }

    /**
     * Initiate intent to start Movie Search
     *
     * @return None
     */
    private fun startMovieSearch() {
        val intent = Intent(this, MovieSearchActivity::class.java)
        startActivity(intent)
    }
}
