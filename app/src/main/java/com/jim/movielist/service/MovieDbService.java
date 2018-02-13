package com.jim.movielist.service;


import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDbService {
    private static final String TAG = MovieDbService.class.getSimpleName();

    private static String API_KEY = "d0ab0388eab220649d79e68a5a3fde0d";

    public static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w200/";

    private static MovieDbService sInstance;
    private MovieDbRetrofit mMovieDbRetrofit;

    public static MovieDbService get() {
        if (sInstance == null) {
            sInstance = new MovieDbService();
        }

        return sInstance;
    }

    private MovieDbService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mMovieDbRetrofit = retrofit.create(MovieDbRetrofit.class);
    }

    public void getNowPlaying(int page, @NonNull final RepoCallback<List<Movie>> callback) {
        Call<NowPlayingResponse> call = mMovieDbRetrofit.getNewPlaying(API_KEY, page);
        call.enqueue(new Callback<NowPlayingResponse>() {
            @Override
            public void onResponse(Call<NowPlayingResponse> call, Response<NowPlayingResponse> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body().getResults());
                } else {
                    String errorMsg = null;
                    if (response.errorBody() != null) {
                        try {
                            errorMsg = response.errorBody().string();
                        } catch (IOException e) {
                            Log.e(TAG, "getNowPlaying.onResponse: ", e );
                        }
                    } else {
                        errorMsg = response.message();
                    }
                    Log.e(TAG, "getNowPlaying.onResponse: " + errorMsg);
                    callback.onError(response.code(), new Exception(errorMsg));
                }
            }

            @Override
            public void onFailure(Call<NowPlayingResponse> call, Throwable t) {
                Log.e(TAG, "getNowPlaying.onFailure: ", t);
                callback.onError(0, t);
            }
        });
    }
}
