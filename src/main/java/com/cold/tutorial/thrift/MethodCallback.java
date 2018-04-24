package com.cold.tutorial.thrift;

import org.apache.thrift.async.AsyncMethodCallback;

/**
 * Created by hui.liao on 2015/12/1.
 */
public class  MethodCallback<T> implements AsyncMethodCallback<T>{

    T response;

    @Override
    public void onComplete(T response) {
        this.response = response;
    }

    @Override
    public void onError(Exception exception) {

    }

    public T getResult() {
        return response;
    }
}
