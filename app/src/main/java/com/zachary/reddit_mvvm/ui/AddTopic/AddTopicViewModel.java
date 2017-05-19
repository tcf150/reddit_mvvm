package com.zachary.reddit_mvvm.ui.AddTopic;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.zachary.reddit_mvvm.base.BaseApiClient;
import com.zachary.reddit_mvvm.service.model.CreateTopicResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tongcheefei on 19/05/2017.
 */

public class AddTopicViewModel extends ViewModel{
    private final static String TAG = "AddTopicViewModel";
    private MutableLiveData<String> mText = new MutableLiveData<>();

    private AddTopicViewModelListener listener;

    public void setListener(AddTopicViewModelListener listener){
        this.listener = listener;
    }

    public LiveData<String> getText(){
        return mText;
    }

    public void onTextChanged(String text) {
        mText.setValue(text);
    }

    public void addTopic() {
        if (mText.getValue().length() == 0) {
            if (listener != null) listener.showEmptyTextToast();
            return;
        }

        Log.d(TAG,"Begin up vote");
        //fire up vote service
        Call<CreateTopicResponse> call = BaseApiClient.getTopicService().createTopic(mText.getValue());
        call.enqueue(new Callback<CreateTopicResponse>() {
            @Override
            public void onResponse(Call<CreateTopicResponse> call, Response<CreateTopicResponse> response) {
                CreateTopicResponse createTopicResponse = response.body();
                Log.d(TAG,"complete up vote");
                if (createTopicResponse != null){
                    if (createTopicResponse.getStatus().isSuccess()){
                        Log.d(TAG,"up vote success");
                        if (listener != null) listener.addTopicSuccessful(createTopicResponse.getStatus().getStatusDesc());
                    }else{
                        Log.d(TAG,"up vote fail");
                        if (listener != null) listener.showErrorToast(createTopicResponse.getStatus().getStatusDesc());
                    }
                }
            }

            @Override
            public void onFailure(Call<CreateTopicResponse> call, Throwable t) {
                t.printStackTrace();
                if (listener != null) listener.showErrorToast(t.getMessage());
            }
        });
    }

    public interface AddTopicViewModelListener{
        void showErrorToast(String message);
        void showEmptyTextToast();
        void addTopicSuccessful(String message);
    }
}
