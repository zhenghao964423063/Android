package com.example.asus.servicebestpratice;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ASUS on 2018/5/7.
 */

public class DownloadTask extends AsyncTask<String,Integer,Integer> {
    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FALSE = 1;
    public static final int TYPE_PAlSE = 2;
    public static final int TYPE_CANCELED = 3;
    private DownloadListener listener;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;
    public DownloadTask(DownloadListener listener){
        this.listener = listener;
    }
    protected Integer doInBackground(String ...params){
        InputStream is = null;
        RandomAccessFile savedFile = null;
        File file = null;
        try{
            long downloadedLength = 0;
            String downloadUrl = params[0];
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(directory+fileName);
            if(file.exists())
                downloadedLength = file.length();
            long contentLength = getContentLength(downloadUrl);
            if(contentLength == 0)
                return TYPE_FALSE;
            else if(contentLength == downloadedLength)
                return TYPE_SUCCESS;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("RANGE","bytes="+downloadedLength+"-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if(response != null){
                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file,"rw");
                savedFile.seek(downloadedLength);
                byte[] b= new byte[1024];
                int total = 0;
                int len;
                while((len = is.read(b))!= -1){
                    if(isCanceled){
                        return TYPE_CANCELED;
                    }
                    else if(isPaused){
                        return TYPE_PAlSE;
                    }else{
                        total += len;
                        savedFile.write(b,0,len);
                        int progress = (int)((total +downloadedLength)*100/contentLength);
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                if(is != null)
                    is.close();
                if(savedFile != null)
                    savedFile.close();
                if(isCanceled && file != null)
                    file.delete();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return  TYPE_FALSE;
    }
    protected void onProgressUpdate(Integer ...valies){
        int progress = valies[0];
        if(progress > lastProgress){
            listener.onprogress(progress);
            lastProgress = progress;
        }
    }
    protected  void onPostExecute(Integer status){
        switch(status){
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FALSE:
                listener.onFailed();
                break;
            case TYPE_PAlSE:
                listener.onPaused();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            default:
                break;
        }
    }
        public void pauseDownload(){
        isPaused = true;
    }
        public void cancelDownload(){
        isCanceled = true;
    }

    private long getContentLength(String downloadUrl)throws IOException{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = client.newCall(request).execute();
        if(response != null && response.isSuccessful()){
            long contentLength = response.body().contentLength();
            response.body().close();;
            return contentLength;
        }
        return 0;
    }
}
