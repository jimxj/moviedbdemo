package com.jim.movielist.movie

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jim.movielist.R
import com.jim.movielist.service.Movie
import com.jim.movielist.service.MovieDbService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.movie_detail.view.*

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a [MovieListActivity]
 * in two-pane mode (on tablets) or a [MovieDetailActivity]
 * on handsets.
 */
class MovieDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var mItem: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mItem = MovieValueHolder.get().pop()
        populateToolbar()
    }

    private fun populateToolbar() {
        activity?.toolbar_layout?.title = mItem?.title

        Picasso.with(context).load(MovieDbService.IMAGE_BASE_URL + mItem?.posterPath)
                .into(activity?.imgPoster)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.movie_detail, container, false)

        // Show the dummy content as text in a TextView.
        mItem?.let {
            rootView.movie_detail.text = it.overview

            if (mItem?.voteAverage != null) {
                rootView.rbAvgRate?.rating = (it.voteAverage / 2.0).toFloat()
            }

            rootView.tvRateNum.text = resources.getString(R.string.rate_count, it.voteCount)
            rootView.tvRealseDate.text = it.releaseDate
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
