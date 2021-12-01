package com.qzl.download

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class DownloadService : Service() {
    private val binder = DownloadServiceBinder()
    //省略其他代码
    inner class DownloadServiceBinder() : Binder(){
        fun getService() = this@DownloadService
    }
    override fun onBind(intent: Intent?): IBinder? = binder
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onCreate() {
        super.onCreate()
        println("服务启动了")
    }

    override fun onDestroy() {
        super.onDestroy()
        print("服务销毁了")
    }
}