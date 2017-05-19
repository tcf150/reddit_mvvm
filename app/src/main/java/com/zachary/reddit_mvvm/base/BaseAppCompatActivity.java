package com.zachary.reddit_mvvm.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by user on 10/5/2017.
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity implements LifecycleRegistryOwner {
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupContentView();
        setupUI();
        setupViewModel();
    }

    /**
     * setup the content layout
     */
    protected abstract void setupContentView();

    /**
     * setup ui logic
     */
    protected abstract void setupUI();

    /**
     * setup view model
     */
    protected abstract void setupViewModel();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * get view by using id
     * @param resId view id
     * @param <T> view class
     * @return T type View
     */
    protected <T extends View> T getView(@IdRes int resId){
        return (T) findViewById(resId);
    }

    /**
     * get view from view by using id
     * @param view rootView
     * @param resId view id
     * @param <T> view class
     * @return T type View
     */
    protected <T extends View> T getViewByView(View view, @IdRes int resId){
        return (T) view.findViewById(resId);
    }

    public LifecycleRegistry getLifecycle() {
        return this.mRegistry;
    }
}
