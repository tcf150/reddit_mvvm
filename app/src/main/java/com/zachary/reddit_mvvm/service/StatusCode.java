package com.zachary.reddit_mvvm.service;

/**
 * Created by user on 10/5/2017.
 */

public class StatusCode {
    public final static int SUCCESS = 0;
    public final static int FAIL_TO_CREATE = 100;
    public final static int FAIL_TO_UPDATE = 101;
    public final static int TOPIC_CANNOT_NULL = 200;
    public final static int TOPIC_ID_CANNOT_NULL = 201;
    public final static int EXCEED_MAX_TOPIC = 202;
    public final static int RECORD_NOT_FOUND = 998;
    public final static int API_ERROR = 999;
}
