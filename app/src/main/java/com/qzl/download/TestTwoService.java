package com.qzl.download;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.task.DownloadTask;
import com.qzl.qdownload.QDownloadAnyModule;
import com.qzl.qdownload.intf.QDownloadIntfComplate;
import com.qzl.qdownload.intf.QDownloadIntfRunning;
import com.qzl.qdownload.intf.QDownloadIntfStart;

import org.jetbrains.annotations.Nullable;

import java.util.Random;

/**
 * Created by Kathy on 17-2-6.
 */
 
public class TestTwoService extends Service {
 
    //client 可以通过Binder获取Service实例
    public class MyBinder extends Binder {
        public TestTwoService getService() {
            return TestTwoService.this;
        }
    }
 
    //通过binder实现调用者client与Service之间的通信
    private MyBinder binder = new MyBinder();
 
    private final Random generator = new Random();
 
    @Override
    public void onCreate() {
        Log.i("Kathy","TestTwoService - onCreate - Thread = " + Thread.currentThread().getName());
        super.onCreate();
        QDownloadAnyModule anyRunnModule = new QDownloadAnyModule(this,new TextView(this));
//        anyRunnModule.start("http://hcjs2ra2rytd8v8np1q.exp.bcevod.com/mda-hegtjx8n5e8jt9zv/mda-hegtjx8n5e8jt9zv.m3u8", FileUtils.getPath("download").toString() + "/test.mp4");
        anyRunnModule.start("http://10.18.104.201:8082/m3u8/e97d6fb3f1b3573bbc7ffc8cc4584ec0.m3u8", FileUtils.getPath("download").toString() + "/test.mp4");
//        anyRunnModule.startOther("http://10.18.104.201:8050/vod/62010201001320002599_62010201001320002599_0_1102010003.flv", FileUtils.getPath("download").toString() + "/test.mp4");
        anyRunnModule.setOnStart(new QDownloadIntfStart() {
            @Override
            public void onStart(@androidx.annotation.Nullable DownloadTask task, @androidx.annotation.Nullable View view) {
                testIntf.test(task.getPercent()+"开始下载");
            }
        });
        anyRunnModule.setOnRunning(new QDownloadIntfRunning() {
            @Override
            public void onRunning(@androidx.annotation.Nullable DownloadTask task, @androidx.annotation.Nullable View view) {
                testIntf.test(task.getPercent()+"");
            }
        });
        anyRunnModule.setOnComplate(new QDownloadIntfComplate() {
            @Override
            public void onComplate(@Nullable DownloadTask task, @Nullable View view) {
                //((TextView)view).setText(task.getFilePath()+"下载完成");
                testIntf.test(task.getFilePath()+"下载完成");
            }
        });
    }
 
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Kathy", "TestTwoService - onStartCommand - startId = " + startId + ", Thread = " + Thread.currentThread().getName());
        return START_NOT_STICKY;
    }
 
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("Kathy", "TestTwoService - onBind - Thread = " + Thread.currentThread().getName());
        return binder;
    }
 
    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("Kathy", "TestTwoService - onUnbind - from = " + intent.getStringExtra("from"));
        return false;
    }
 
    @Override
    public void onDestroy() {
        Log.i("Kathy", "TestTwoService - onDestroy - Thread = " + Thread.currentThread().getName());
        super.onDestroy();
    }
 
    //getRandomNumber是Service暴露出去供client调用的公共方法
    public int getRandomNumber() {
        return generator.nextInt();
    }

    private TestIntf testIntf;

    public void setTestIntf(TestIntf testIntf) {
        this.testIntf = testIntf;
    }
}

interface TestIntf{
    public void test(String str);
}