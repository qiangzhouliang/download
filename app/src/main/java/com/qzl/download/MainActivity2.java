package com.qzl.download;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.arialyy.aria.core.task.DownloadTask;
import com.qzl.qdownload.QDownloadAnyModule;
import com.qzl.qdownload.intf.QDownloadIntfComplate;

import org.jetbrains.annotations.Nullable;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QDownloadAnyModule anyRunnModule = new QDownloadAnyModule(this,findViewById(R.id.tv));
        anyRunnModule.start("http://hcjs2ra2rytd8v8np1q.exp.bcevod.com/mda-hegtjx8n5e8jt9zv/mda-hegtjx8n5e8jt9zv.m3u8", FileUtils.getPath("download").toString() + "/test.mp4");
        anyRunnModule.setOnComplate(new QDownloadIntfComplate() {
            @Override
            public void onComplate(@Nullable DownloadTask task, @Nullable View view) {
                ((TextView)view).setText(task.getFilePath()+"下载完成");
            }
        });
    }
}