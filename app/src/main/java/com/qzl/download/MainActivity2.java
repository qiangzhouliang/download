package com.qzl.download;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.arialyy.aria.core.task.DownloadTask;
import com.qzl.qdownload.QDownloadAnyModule;
import com.qzl.qdownload.intf.QDownloadIntfComplate;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private TestTwoService service = null;
    private boolean isBind = false;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            isBind = true;
            TestTwoService.MyBinder myBinder = (TestTwoService.MyBinder) binder;
            service = myBinder.getService();
            Log.i("Kathy", "ActivityA - onServiceConnected");
            int num = service.getRandomNumber();
            Log.i("Kathy", "ActivityA - getRandomNumber = " + num);
            service.setTestIntf(new TestIntf() {
                @Override
                public void test(String str) {
                    System.out.println("=====》"+str);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
            Log.i("Kathy", "ActivityA - onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*QDownloadAnyModule anyRunnModule = new QDownloadAnyModule(this,findViewById(R.id.tv));
        anyRunnModule.start("http://hcjs2ra2rytd8v8np1q.exp.bcevod.com/mda-hegtjx8n5e8jt9zv/mda-hegtjx8n5e8jt9zv.m3u8", FileUtils.getPath("download").toString() + "/test.mp4");
        anyRunnModule.setOnComplate(new QDownloadIntfComplate() {
            @Override
            public void onComplate(@Nullable DownloadTask task, @Nullable View view) {
                ((TextView)view).setText(task.getFilePath()+"下载完成");
            }
        });*/

        Intent intent = new Intent(this, TestTwoService.class);
        intent.putExtra("from", "ActivityA");
        bindService(intent, conn, BIND_AUTO_CREATE);




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBind) {
            Log.i("Kathy",
                "----------------------------------------------------------------------");
            Log.i("Kathy", "ActivityA 执行 unbindService");
            //unbindService(conn);
        }
    }
}