package shire.the.great.nodepi.tasks;

/**
 * Created by ZachS on 11/6/2016.
 */

public interface AsyncResult<Result> {
    void onComplete(Result output);
}
