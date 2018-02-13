package com.jim.movielist.movies;


import android.support.annotation.NonNull;
import android.util.Log;

import com.jim.movielist.service.Movie;
import com.jim.movielist.service.MovieDbService;
import com.jim.movielist.service.RepoCallback;

import java.util.List;

public class MovieListPresenter implements MoviesContract.Presenter {
    private static final String TAG = "MovieListPresenter";

    private final MoviesContract.View mView;
    private int mCurrentPage = 1;

    public MovieListPresenter(MoviesContract.View view) {
        mView = view;
    }

    @Override
    public void loadMovies() {
        mView.setLoadingIndicator(true);
        Log.d(TAG, "loadMovies: mCurrentPage=" + mCurrentPage);
        MovieDbService.get().getNowPlaying(mCurrentPage, new RepoCallback<List<Movie>>() {
            @Override
            public void onResult(List<Movie> result) {
                mView.setLoadingIndicator(false);

                mView.showMovies(result);
            }

            @Override
            public void onError(int code, Throwable error) {
                mView.setLoadingIndicator(false);
            }
        });

        mCurrentPage++;
    }

    @Override
    public void openMovieDetails(@NonNull Movie movie) {

    }

    @Override
    public void start() {
        loadMovies();
    }
}
