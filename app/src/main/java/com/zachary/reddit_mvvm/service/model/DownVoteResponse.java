package com.zachary.reddit_mvvm.service.model;

import com.zachary.reddit_mvvm.base.BaseResponse;

/**
 * Created by user on 10/5/2017.
 */

public class DownVoteResponse extends BaseResponse {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

