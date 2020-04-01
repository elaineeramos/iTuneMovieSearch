package com.ever.itunesmoviesearch.moviesearch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ever.itunesmoviesearch.moviedetails.MovieDetailsActivity
import com.ever.itunesmoviesearch.R
import com.ever.itunesmoviesearch.moviesearch.recycler.MovieListAdapter
import com.ever.itunesmoviesearch.utility.disposedBy
import com.ever.itunesmoviesearch.viewmodel.MovieSearchViewModel
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

    lateinit var adapter: MovieListAdapter
    lateinit var viewModel : MovieSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)

        attachUI()

        startMovieSearchViewModel()

        GlobalScope.launch {
            checkDatabase()
        }
    }

    /**
     * Attach the movie recycler view to the activity
     *
     * @param None
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
     * @param None
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
     * @param None
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
     * Initiate movie search viewmodel
     *
     * @param None
     * @return None
     */
    private fun startMovieSearchViewModel () {
        viewModel = ViewModelProviders.of(this).get(MovieSearchViewModel::class.java)
    }

    //region EVER TESTING TODO MOVE THIS!!!!
    /**
     * Checks the database
     * If there is no movie description in the database, movie is queried to iTunes
     * If there is movie description in the database, the saved data is used
     *
     * @param None
     * @return None
     */
    private fun checkDatabase() {
        if (viewModel.getMovieDescriptionCount() != 0) {
            loadData()
        }
        else{
            saveData()
        }
    }

    /**
     * Save the obtained data from iTunes query and load the data to repository
     *
     * @param None
     * @return None
     */
    private fun saveData() {
        println("EVER TESTING save data")
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
     * @param None
     * @return None
     */
    private fun loadData() {
        println("EVER TESTING load data")
        viewModel.getMovieDetail()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { movieDetails ->
                adapter.movieDescription.accept(movieDetails.toMutableList())
            }
            .disposedBy(bag)
    }
    //endregion EVER TESTING TODO MOVE THIS!!!!

    /**
     * Clear and dispose all the contained Disposables in the application
     *
     * @param None
     * @return None
     */
    override fun onDestroy() {
        super.onDestroy()
        bag.clear()
    }
}