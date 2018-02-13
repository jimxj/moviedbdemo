package com.jim.movielist.service;


public interface RepoCallback<T> {

    void onResult(T result);

    void onError(int code, Throwable error);

}
