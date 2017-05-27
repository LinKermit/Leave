package com.lin.leave.down;

/**
 * Created by Lin on 2017/5/2.
 */

public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
