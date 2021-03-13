package com.qzl.download

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import com.arialyy.aria.core.task.DownloadTask
import com.qzl.qdownload.QDownloadAnyModule
import com.qzl.qdownload.intf.QDownloadIntfComplate


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myRequetPermission();

    }

    private fun myRequetPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        } else {
            Toast.makeText(this, "您已经申请了权限!", Toast.LENGTH_SHORT).show()
            val anyRunnModule = QDownloadAnyModule(this)
            anyRunnModule.start("http://hcjs2ra2rytd8v8np1q.exp.bcevod.com/mda-hegtjx8n5e8jt9zv/mda-hegtjx8n5e8jt9zv.m3u8", FileUtils.getPath("download").toString() + "/test.mp4");
            anyRunnModule.onComplate = object :QDownloadIntfComplate{
                override fun onComplate(task: DownloadTask?,view:View?) {
                    findViewById<TextView>(R.id.tv).setText(task?.downloadEntity?.filePath+"下载完成")
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            val anyRunnModule = QDownloadAnyModule(this);
            anyRunnModule.start("http://hcjs2ra2rytd8v8np1q.exp.bcevod.com/mda-hegtjx8n5e8jt9zv/mda-hegtjx8n5e8jt9zv.m3u8", FileUtils.getPath("download").toString() + "/test.mp4");
        }
    }

}