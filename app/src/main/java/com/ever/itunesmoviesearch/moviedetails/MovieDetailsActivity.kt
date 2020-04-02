package com.ever.itunesmoviesearch.moviedetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.ever.itunesmoviesearch.R
import com.ever.itunesmoviesearch.databinding.ActivityMovieDetailBinding
import com.ever.itunesmoviesearch.model.moviedata.MovieDescription
import com.ever.itunesmoviesearch.utility.disposedBy
import com.ever.itunesmoviesearch.viewmodel.MovieDetailsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Class responsible for displaying movie details
 * Use data binding to set movie details
 *
 */
class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding

    private val bag = CompositeDisposable()

    private val movieIndexExtra : String = "movie_index"

    private lateinit var viewModel : MovieDetailsViewModel

    /**
     * Initialize movie details page of the application
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        startMovieDetailViewModel()

        val movieIndex : Int = intent.getStringExtra(movieIndexExtra)!!.toInt()

        GlobalScope.launch {
            loadMovieDescription(movieIndex)
        }
    }

    /**
     * Initiate movie detail view model
     *
     * @return None
     */
    private fun startMovieDetailViewModel () {
        viewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)
    }

    /**
     * Obtain movie description and display it
     *
     * @param movieIndex
     * @return None
     */
    private fun loadMovieDescription(movieIndex: Int) {
        viewModel.getMovieFullDetail(movieIndex)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { movieDetail ->
                setMovieDetail(movieDetail)
            }
            .disposedBy(bag)
    }

    /**
     * Set movie details obtained from repository
     * to the text and image views
     *
     * @param movieDescription
     * @return None
     */
    private fun setMovieDetail(movieDescription: MovieDescription) {
        setMovieTextDetail(movieDescription)
        setMoviePoster(movieDescription)
    }

    /**
     * Set all text views on movie details page
     *
     * @param movieDescription
     * @return None
     */
    private fun setMovieTextDetail(movieDescription: MovieDescription) {
        val actionBar = supportActionBar
        actionBar!!.title = movieDescription.trackName
        binding.movieDetail = movieDescription
    }

    /**
     * Set image view on movie details page by using Glide
     * Image is obtained by using the URL provided by iTunes
     * When image is not yet fully downloaded, placeholder image is used
     *
     * @param movieDescription
     * @return None
     */
    private fun setMoviePoster(movieDescription: MovieDescription) {
        Glide.with(applicationContext)
            .load(movieDescription.artwork)
            .placeholder(R.drawable.ic_movie_roll)
            .error(R.drawable.ic_movie_roll)
            .dontAnimate()
            .into(binding.movieDetailPoster)
    }

    /**
     * When back button is pressed, the application goes back to
     * the previous activity, which shows lists of movies
     *
     * @return None
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        bag.clear()
        return super.onSupportNavigateUp()
    }
}