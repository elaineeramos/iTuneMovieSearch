package com.ever.itunesmoviesearch.moviesearch.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ever.itunesmoviesearch.R
import com.ever.itunesmoviesearch.databinding.ItemMovieOverviewBinding
import com.ever.itunesmoviesearch.model.moviedata.MovieDescription
import com.ever.itunesmoviesearch.utility.disposedBy
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

typealias ItemClickedLambda = (v: View, position: Int) -> Unit

/**
 * Class responsible for movie list for recycler view
 *
 */
class MovieListAdapter(var onItemClicked: ItemClickedLambda): RecyclerView.Adapter<MovieListHolder>() {
    private lateinit var binding: ItemMovieOverviewBinding

    internal val movieDescription = BehaviorRelay.createDefault(mutableListOf<MovieDescription>())
    private val bag = CompositeDisposable()

    init {
        movieDescription.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                notifyDataSetChanged()
            }.disposedBy(bag)
    }

    /**
     * On creation of the adapter, set the layout for the list item
     * and set the click listener
     *
     * @param parent parent view group
     * @param viewType type of new view
     * @return created view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_movie_overview, parent, false)

        val viewHolder = MovieListHolder(binding, parent.context)
        binding.messageCardView.setOnClickListener { v ->
            onItemClicked(v, viewHolder.adapterPosition)
        }

        return viewHolder
    }

    /**
     * Set the details of the movie list holder
     *
     * @param holder the holder to be updated
     * @param position position of the movie in the item
     * @return None
     */
    override fun onBindViewHolder(holder: MovieListHolder, position: Int) {
        holder.setMovieDetail(movieDescription.value[position])
    }

    /**
     * Obtain the number of items in the adapter
     *
     * @return number of movies in the adapter
     */
    override fun getItemCount(): Int = movieDescription.value.size
}