package com.jim.movielist.movies;


import android.support.annotation.NonNull;

import com.jim.movielist.mvp.BasePresenter;
import com.jim.movielist.mvp.BaseView;
import com.jim.movielist.service.Movie;

import java.util.List;

public interface MoviesContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void showMovies(List<Movie> movies);

        void showMovieDetails(Movie movie);
    }

    interface Presenter extends BasePresenter {
        void loadMovies();

        void openMovieDetails(@NonNull Movie movie);
    }
}
