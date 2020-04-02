package com.ever.itunesmoviesearch.moviesearch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ever.itunesmoviesearch.moviedetails.MovieDetailsActivity
import com.ever.itunesmoviesearch.R
import com.ever.itunesmoviesearch.moviesearch.recycler.MovieListAdapter
import com.ever.itunesmoviesearch.utility.disposedBy
import com.ever.itunesmoviesearch.viewmodel.MovieSearchViewModel
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_movie_search.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Class responsible for displaying the movie search result
 *
 */
class MovieSearchActivity : AppCompatActivity() {

    private val bag = CompositeDisposable()

    private lateinit var adapter: MovieListAdapter
    private lateinit var viewModel : MovieSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)

        attachUI()

        startMovieSearchViewModel()

        initializeSnackbar()

        GlobalScope.launch {
            checkDatabase()
        }
    }

    /**
     * Attach the movie recycler view to the activity
     *
     * @return None
     */
    private fun attachUI() {
        val linearLayoutManager = LinearLayoutManager(this)

        movieRecyclerView.layoutManager = linearLayoutManager
        movieRecyclerView.setHasFixedSize(true)

        initializeListView()
    }

    /**
     * Initialize the movie search list view
     * Set the adapter to recycler view
     * Set the method to be performed when item is clicked
     *
     * @return None
     */
    private fun initializeListView() {
        adapter = MovieListAdapter { view, position -> rowTapped(position) }
        movieRecyclerView.adapter = adapter
    }

    /**
     * Starts activity to display more details on the selected movie
     * Update the access time for the movie description
     *
     * @return None
     */
    private fun rowTapped(position: Int) {
        GlobalScope.launch {
            viewModel.updateMovieDescriptionAccessDate(position)
        }

        val intent = Intent(this, MovieDetailsActivity::class.java)
            .apply {
                putExtra("movie_index", position.toString())
            }

        startActivity(intent)
    }

    /**
     * Initiate movie search view model
     *
     * @return None
     */
    private fun startMovieSearchViewModel () {
        viewModel = ViewModelProviders.of(this).get(MovieSearchViewModel::class.java)
    }

    /**
     * Checks the database
     * If there is no movie description in the database, movie is queried to iTunes
     * If there is movie description in the database, the saved data is used
     *
     * @return None
     */
    private fun checkDatabase() {
        if (viewModel.getMovieDescriptionCount() != 0) {
            loadData()
        }
        else if(viewModel.isDeviceConnectedToNetwork(applicationContext)) {
            saveData()
        }
    }

    /**
     * Save the obtained data from iTunes query and load the data to repository
     *
     * @return None
     */
    private fun saveData() {
        viewModel.getMessagesRx()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { movieDetails ->
                viewModel.setMovieToDatabase(movieDetails)
                loadData()
            }
            .disposedBy(bag)
    }

    /**
     * load data from repository to adapter
     *
     */
    private fun loadData() {
        viewModel.getMovieDetail()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { movieDetails ->
                adapter.movieDescription.accept(movieDetails.toMutableList())
            }
            .disposedBy(bag)
    }

    /**
     * Initialize snackbar
     * When device is detected to have no internet connection during application boot-up,
     * snackbar notification appears
     *
     */
    private fun initializeSnackbar() {
        viewModel.snackbarNotificationData.observe(this, Observer {
            if (it == true) {
                Snackbar.make(
                    this!!.findViewById(android.R.id.content),
                    "No Internet Connection",
                    Snackbar.LENGTH_LONG
                ).show()
                viewModel.doneShowingSnackbar()
            }
        })
    }

    /**
     * Clear and dispose all the contained Disposables in the application
     *
     * @return None
     */
    override fun onDestroy() {
        super.onDestroy()
        bag.clear()
    }
}