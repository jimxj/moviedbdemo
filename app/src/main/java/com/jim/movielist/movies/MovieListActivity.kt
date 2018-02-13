package com.jim.movielist.movies

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jim.movielist.R
import com.jim.movielist.movie.MovieDetailActivity
import com.jim.movielist.movie.MovieDetailFragment
import com.jim.movielist.movie.MovieValueHolder
import com.jim.movielist.service.Movie
import com.jim.movielist.service.MovieDbService
import com.jim.movielist.utils.StringUtils
import com.jim.movielist.view.EndlessScrollListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.movie_list.*
import kotlinx.android.synthetic.main.movie_list_content.view.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [MovieDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MovieListActivity : AppCompatActivity(), MoviesContract.View {
    val TAG = "MovieListActivity"

    override fun setPresenter(presenter: MoviesContract.Presenter?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLoadingIndicator(active: Boolean) {

    }

    override fun showMovies(movies: List<Movie>?) {
        if (movies != null) {
            mAdapter.addMovies(movies)
        }
    }

    override fun showMovieDetails(movie: Movie?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane: Boolean = false

    private lateinit var mPresenter: MoviesContract.Presenter

    private lateinit var mAdapter: MovieItemRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (movie_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }

        mAdapter = MovieItemRecyclerViewAdapter(this, mTwoPane)
        setupRecyclerView(movie_list, mAdapter)

        val scrollListener = object: EndlessScrollListener(movie_list.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                Log.d(TAG, "onLoadMore: page=${page}, totalItemsCount=${totalItemsCount}")
                mPresenter.loadMovies()
            }
        };
        movie_list.addOnScrollListener(scrollListener)

        mPresenter = MovieListPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, adapter: MovieItemRecyclerViewAdapter) {
        recyclerView.adapter = adapter
    }

    class MovieItemRecyclerViewAdapter(private val mParentActivity: MovieListActivity,
                                       private val mTwoPane: Boolean) :
            RecyclerView.Adapter<MovieItemRecyclerViewAdapter.ViewHolder>() {

        private val mOnClickListener: View.OnClickListener
        private val mValues: MutableList<Movie>

        init {
            mValues = ArrayList()

            mOnClickListener = View.OnClickListener { v ->
                val item = v.tag as Movie
                if (mTwoPane) {
                    val fragment = MovieDetailFragment().apply {
                        arguments = Bundle().apply {
                            putInt(MovieDetailFragment.ARG_ITEM_ID, item.id)
                        }
                    }
                    mParentActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.movie_detail_container, fragment)
                            .commit()
                } else {
                    val intent = Intent(v.context, MovieDetailActivity::class.java)
                    MovieValueHolder.get().value = item
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.movie_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = mValues[position]
            holder.mTitleView.text = item.title
            holder.mContentView.text = StringUtils.truncate(item.overview, 120)
            Picasso.with(holder.mContentView.context).load(MovieDbService.IMAGE_BASE_URL + item.posterPath)
                    .into(holder.mImage)

            with(holder.itemView) {
                tag = item
                setOnClickListener(mOnClickListener)
            }
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        fun addMovies(movies: List<Movie>) {
            val originalSize = mValues.size
            mValues.addAll(movies)
            notifyItemRangeInserted(originalSize, movies.size)
        }

        inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
            val mTitleView: TextView = mView.id_title
            val mContentView: TextView = mView.content
            val mImage: ImageView = mView.image
        }
    }
}
