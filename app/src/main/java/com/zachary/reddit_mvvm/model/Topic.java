package com.zachary.reddit_mvvm.model;

import com.zachary.reddit_mvvm.base.BaseModel;

/**
 * Created by user on 10/5/2017.
 */

public class Topic extends BaseModel {
    private int id;
    private String title;
    private int count;
    private String createAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public boolean equalId(int id){
        return (this.id == id);
    }
}
