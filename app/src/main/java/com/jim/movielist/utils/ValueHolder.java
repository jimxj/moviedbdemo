package com.jim.movielist.utils;


import java.util.concurrent.atomic.AtomicReference;

public class ValueHolder<T> {
    private final AtomicReference<T> mReference;

    public ValueHolder(T value) {
        mReference = new AtomicReference<>(value);
    }

    public ValueHolder() {
        mReference = new AtomicReference<>();
    }

    public T getValue() {
        return mReference.get();
    }

    public void setValue(T value) {
        mReference.set(value);
    }


    public T pop() {
        T result = mReference.get();
        mReference.set(null);

        return result;
    }
}
