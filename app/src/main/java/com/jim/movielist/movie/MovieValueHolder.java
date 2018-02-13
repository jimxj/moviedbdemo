package com.jim.movielist.movie;


import com.jim.movielist.service.Movie;
import com.jim.movielist.utils.ValueHolder;

public class MovieValueHolder extends ValueHolder<Movie> {
    private static MovieValueHolder sInstance;

    private MovieValueHolder() {
    }

    public static MovieValueHolder get() {
        if (sInstance == null) {
            sInstance = new MovieValueHolder();
        }

        return sInstance;
    }
}
