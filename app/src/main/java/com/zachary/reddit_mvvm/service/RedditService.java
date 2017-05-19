package com.zachary.reddit_mvvm.service;

import com.zachary.reddit_mvvm.service.model.CreateTopicResponse;
import com.zachary.reddit_mvvm.service.model.DownVoteResponse;
import com.zachary.reddit_mvvm.service.model.GetTopicListResponse;
import com.zachary.reddit_mvvm.service.model.UpVoteResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by user on 10/5/2017.
 */

public interface RedditService {
    @GET("/getTopicList.php")
    Call<GetTopicListResponse> getTopicList(@Query("limit") int limit, @Query("offset") int offset);

    @FormUrlEncoded
    @POST("/createTopic.php")
    Call<CreateTopicResponse> createTopic(@Field("topicTitle") String topicTitle);

    @FormUrlEncoded
    @POST("/upVote.php")
    Call<UpVoteResponse> upVote(@Field("topicId") int topicId);

    @FormUrlEncoded
    @POST("/downVote.php")
    Call<DownVoteResponse> downVote(@Field("topicId") int topicId);
}
