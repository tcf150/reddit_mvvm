package com.zachary.reddit_mvvm.ui.Main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.zachary.reddit_mvvm.base.BaseApiClient;
import com.zachary.reddit_mvvm.data.DataManager;
import com.zachary.reddit_mvvm.model.Topic;
import com.zachary.reddit_mvvm.service.model.DownVoteResponse;
import com.zachary.reddit_mvvm.service.model.GetTopicListResponse;
import com.zachary.reddit_mvvm.service.model.UpVoteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tongcheefei on 19/05/2017.
 */

public class MainViewModel extends ViewModel {
    private final static String TAG = "MainViewModel";
    private MutableLiveData<List<Topic>> topicList = new MutableLiveData<>();

    private MainViewModelListener listener;

    public void setListener(MainViewModelListener listener){
        this.listener = listener;
    }

    public LiveData<List<Topic>> getTopicList() {
        return topicList;
    }

    public void updateVote(final int topicId){
        //todo MainRepository
        if (topicId < 0) return;
        Log.d(TAG,"Begin up vote");
        //fire up vote service
        Call<UpVoteResponse> call = BaseApiClient.getTopicService().upVote(topicId);
        call.enqueue(new Callback<UpVoteResponse>() {
            @Override
            public void onResponse(Call<UpVoteResponse> call, Response<UpVoteResponse> response) {
                UpVoteResponse upVoteResponse = response.body();
                Log.d(TAG,"complete up vote");
                if (upVoteResponse != null){
                    if (upVoteResponse.getStatus().isSuccess()){
                        Log.d(TAG,"up vote success");
                        //update DataManager
                        DataManager.getInstance().updateTopicCount(topicId,upVoteResponse.getCount());
                        topicList.setValue(DataManager.getInstance().getmTopicList());
                    }else{
                        Log.d(TAG,"up vote fail");
                        if (listener != null) listener.showErrorToast(upVoteResponse.getStatus().getStatusDesc());
                    }
                }
            }

            @Override
            public void onFailure(Call<UpVoteResponse> call, Throwable t) {
                t.printStackTrace();
                if (listener != null) listener.showErrorToast(t.getMessage());
            }
        });
    }

    public void downVote(final int topicId){
        //todo MainRepository
        if (topicId < 0) return;
        Log.d(TAG,"begin down vote");
        //fire down vote service
        Call<DownVoteResponse> call = BaseApiClient.getTopicService().downVote(topicId);
        call.enqueue(new Callback<DownVoteResponse>() {
            @Override
            public void onResponse(Call<DownVoteResponse> call, Response<DownVoteResponse> response) {
                DownVoteResponse downVoteResponse = response.body();
                Log.d(TAG,"down vote complete");
                if (downVoteResponse != null){
                    if (downVoteResponse.getStatus().isSuccess()){
                        Log.d(TAG,"down vote success");
                        //update DataManager
                        DataManager.getInstance().updateTopicCount(topicId,downVoteResponse.getCount());
                        topicList.setValue(DataManager.getInstance().getmTopicList());
                    }else{
                        Log.d(TAG,"down vote fail");
                        if (listener != null) listener.showErrorToast(downVoteResponse.getStatus().getStatusDesc());
                    }
                }
            }

            @Override
            public void onFailure(Call<DownVoteResponse> call, Throwable t) {
                t.printStackTrace();
                if (listener != null) listener.showErrorToast(t.getMessage());
            }
        });
    }

    public void refreshTopicList(){
        //clear list if force refresh data
        DataManager.getInstance().clearTopicLis();
        topicList.setValue(DataManager.getInstance().getmTopicList());

        Log.d(TAG,"begin get topic list");
        //fire getTopicList
        Call<GetTopicListResponse> call = BaseApiClient.getTopicService().getTopicList(20,0);
        call.enqueue(new Callback<GetTopicListResponse>() {
            @Override
            public void onResponse(Call<GetTopicListResponse> call, Response<GetTopicListResponse> response) {
                GetTopicListResponse getTopicListResponse = response.body();
                Log.d(TAG,"get topic list complete");
                if (getTopicListResponse != null){
                    if (getTopicListResponse.getStatus().isSuccess()){
                        //update data manager and notify ui
                        Log.d(TAG,"get topic list success");
                        DataManager.getInstance().addAllTopic(getTopicListResponse.getTopicList());
                        topicList.setValue(DataManager.getInstance().getmTopicList());
                    }else{
                        Log.d(TAG,"get topic list fail");
                        if (listener != null) listener.showErrorToast(getTopicListResponse.getStatus().getStatusDesc());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetTopicListResponse> call, Throwable t) {
                t.printStackTrace();
                if (listener != null) listener.showErrorToast(t.getMessage());
            }
        });
    }

    public interface MainViewModelListener{
        void showErrorToast(String message);
    }
}
