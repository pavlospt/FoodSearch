package com.lifesum.foodsearch.presenters.interfaces;

/**
 * Created by PavlosPT13.
 */
public interface IPresenter<V> {
    void attachView(V view);
    void detachView();
}
