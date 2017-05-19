package com.zachary.reddit_mvvm.ui.Main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.zachary.reddit_mvvm.R;
import com.zachary.reddit_mvvm.base.BaseAppCompatActivity;
import com.zachary.reddit_mvvm.model.Topic;
import com.zachary.reddit_mvvm.ui.AddTopic.AddTopicActivity;

import java.util.List;

public class MainActivity extends BaseAppCompatActivity implements MainViewModel.MainViewModelListener{
    private MainViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvTopic;
    private TopicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setupUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTopicActivity.class);
               startActivity(intent);
            }
        });

        swipeRefreshLayout = getView(R.id.swipeRefreshLayout);
        rvTopic = getView(R.id.rvTopic);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refreshTopicList();
            }
        });

        //setup layout manager for recyclerview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTopic.setLayoutManager(linearLayoutManager);

        //add divider for recyclerview
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        rvTopic.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void setupViewModel(){
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.setListener(this);
        viewModel.getTopicList().observe(this, new Observer<List<Topic>>() {
            @Override
            public void onChanged(@Nullable List<Topic> topics) {
                adapter.setTopicList(topics);
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }
        });

        //set adapter for recycler view and onClickListener
        adapter = new TopicAdapter(viewModel.getTopicList().getValue());
        adapter.setOnClickListener(new TopicAdapter.OnClickListener() {
            @Override
            public void onUpClick(int topicId) {
                viewModel.updateVote(topicId);
            }

            @Override
            public void onDownClick(int topicId) {
                viewModel.downVote(topicId);
            }
        });
        rvTopic.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.refreshTopicList();
    }

    @Override
    public void showErrorToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
