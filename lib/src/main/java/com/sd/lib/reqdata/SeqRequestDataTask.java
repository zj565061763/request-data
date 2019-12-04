package com.sd.lib.reqdata;

/**
 * 如果在执行中，则不能重复发起的任务
 *
 * @param <T>
 */
public abstract class SeqRequestDataTask<T> extends BaseRequestDataTask<T>
{
    @Override
    public void execute(ExecuteCallback<T> callback)
    {
        if (getState() == State.Executing)
            return;

        super.execute(callback);
    }
}
