package com.jim.movielist.service;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDbRetrofit {

    @GET("movie/now_playing")
    Call<NowPlayingResponse> getNewPlaying(@Query("api_key") String apiKey, @Query("page") int page);
}
