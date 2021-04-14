package com.example.smalltranslation.data.base;

public interface OnDataLoadedListener<T> {
    void onSuccess(T data);

    void onFailure(Exception exception);
}
