package com.yuyang.volley_packaging.base;

import com.android.volley.VolleyError;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 错误包装类，VolleyError是Volley抛出的原始错误
 */

public class PeonyError {
    public final static int ERROR_NETWORK = 1;
    public final static int ERROR_SERVER = 2;
    public final static int ERROR_TIMEOUT = 3;
    public final static int ERROR_PARSE = 4;
    public final static int ERROR_NO_CONNECTION = 10;

    private VolleyError mVolleyError;
    private int mErrorType;

    public PeonyError(VolleyError mVolleyError) {
        this.mVolleyError = mVolleyError;
    }

    /**
     * 获取错误信息
     * @return
     */
    public String getErrorMessage() {
        return mVolleyError.getMessage();
    }

    /**
     * 输出错误信息
     */
    public void printStackTrace() {
        mVolleyError.printStackTrace();
    }

    public void printStackTrace(PrintStream err) {
        mVolleyError.printStackTrace(err);
    }

    public void printStackTrace(PrintWriter err) {
        mVolleyError.printStackTrace(err);
    }

    public String toString() {
        return mVolleyError.toString();
    }

    public int getErrorType() {
        return mErrorType;
    }

    public void setErrorType(int errorType) {
        this.mErrorType = errorType;
    }

}
