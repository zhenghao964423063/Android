package com.example.asus.servicebestpratice;

/**
 * Created by ASUS on 2018/5/6.
 */

public interface DownloadListener {
    void onprogress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
