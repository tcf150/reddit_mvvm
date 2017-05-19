package com.zachary.reddit_mvvm.service.model;

import com.zachary.reddit_mvvm.base.BaseResponse;
import com.zachary.reddit_mvvm.model.Topic;

import java.util.List;

/**
 * Created by user on 10/5/2017.
 */

public class GetTopicListResponse extends BaseResponse {
    private List<Topic> topicList;

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }
}
