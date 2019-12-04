package com.sd.lib.reqdata;

public abstract class BaseRequestDataTask<T> implements RequestDataTask<T>
{
    private State mState = State.None;
    private OnStateChangeCallback mOnStateChangeCallback;
    private ExecuteCallback<T> mExecuteCallback;

    @Override
    public final State getState()
    {
        return mState;
    }

    @Override
    public final void setOnStateChangeCallback(OnStateChangeCallback callback)
    {
        mOnStateChangeCallback = callback;
    }

    /**
     * 设置状态
     *
     * @param state
     */
    protected final void setState(State state)
    {
        if (state == null)
            throw new IllegalArgumentException("state is null");

        final State old = mState;
        if (old != state)
        {
            mState = state;
            onStateChanged(old, mState);
            if (mOnStateChangeCallback != null)
                mOnStateChangeCallback.onStateChanged(old, mState);
        }
    }

    protected void onStateChanged(State oldState, State newState)
    {
    }

    protected final ExecuteCallback<T> getExecuteCallback()
    {
        if (mExecuteCallback == null)
        {
            mExecuteCallback = new ExecuteCallback<T>()
            {
                @Override
                public void onSuccess(T data)
                {
                }

                @Override
                public void onError(int code, String desc)
                {
                }
            };
        }
        return mExecuteCallback;
    }

    final void setExecuteCallback(ExecuteCallback<T> callback)
    {
        mExecuteCallback = callback;
    }

    @Override
    public void execute(ExecuteCallback<T> callback)
    {
        setExecuteCallback(callback);
        executeImpl(callback);
    }

    @Override
    public final void cancel()
    {
        mExecuteCallback = null;
        cancelImpl();
    }

    protected abstract void executeImpl(ExecuteCallback<T> callback);

    protected abstract void cancelImpl();
}
