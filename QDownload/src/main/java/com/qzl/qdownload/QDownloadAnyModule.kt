package com.qzl.qdownload

import android.content.Context
import android.net.Uri
import android.view.View
import com.arialyy.annotations.Download
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.download.m3u8.M3U8VodOption
import com.arialyy.aria.core.download.target.HttpBuilderTarget
import com.arialyy.aria.core.processor.ILiveTsUrlConverter
import com.arialyy.aria.core.processor.IVodTsUrlConverter
import com.arialyy.aria.core.task.DownloadTask
import com.qzl.qdownload.intf.*
import java.util.*

/**
 * @author qiangzhouliang
 * @desc 下载m3u8公共类
 * @email 2538096489@qq.com
 * @time 2021/3/13 14:32
 * @class AnyRunnModule
 * @package com.zdww.smartvideo.main.videoback.m3u8
 */
class QDownloadAnyModule(context: Context, view: View? = null) {
    private val mContext: Context
    val mView:View?
    var mUrl: String? = null
    var mTaskId: Long = -1
    var onWait:QDownloadIntfWait? = null
    var onPre: QDownloadIntfPre? = null
    var onStart: QDownloadIntfStart? = null
    var onRunning: QDownloadIntfRunning? = null
    var onResume: QDownloadIntfResume? = null
    var onStop: QDownloadIntfStop? = null
    var onCancle: QDownloadIntfCancle? = null
    var onFail: QDownloadIntfFail? = null
    var onComplate: QDownloadIntfComplate? = null
    var vodTsUrlConverter: IVodTsUrlConverter? = null

    @Download.onWait
    fun onWait(task: DownloadTask) {
        onWait?.onWait(task, mView)
    }

    @Download.onPre
    fun onPre(task: DownloadTask?) {
        onPre?.onPre(task, mView)
    }

    @Download.onTaskStart
    fun taskStart(task: DownloadTask?) {
        onStart?.onStart(task, mView)
    }

    @Download.onTaskRunning
    fun running(task: DownloadTask) {
        val p = task.percent //任务进度百分比
        val speed = task.convertSpeed //转换单位后的下载速度，单位转换需要在配置文件中打开
        val speed1 = task.speed //原始byte长度速度
        onRunning?.onRunning(task, mView)
    }

    @Download.onTaskResume
    fun taskResume(task: DownloadTask?) {
        onResume?.onResume(task, mView)
    }

    @Download.onTaskStop
    fun taskStop(task: DownloadTask?) {
        onStop?.onStop(task, mView)
    }

    @Download.onTaskCancel
    fun taskCancel(task: DownloadTask?) {
        onCancle?.onCancle(task, mView)
    }

    @Download.onTaskFail
    fun taskFail(task: DownloadTask?) {
        onFail?.onFail(task, mView)
    }

    @Download.onTaskComplete
    fun taskComplete(task: DownloadTask) {
        onComplate?.onComplate(task, mView)
    }

    /**
     * @desc 自定义进程下载
     * @author QZL
     * @time 2021/3/13 17:11
     */
    fun start(httpBuilderTarget: HttpBuilderTarget) {
        mTaskId = httpBuilderTarget.create()
    }

    /**
     * @desc 使用现有的进程下载
     * @author QZL
     * @time 2021/3/13 17:12
     */
    fun start(url: String?, FilePath: String?) {
        mUrl = url
        // 创建m3u8直播文件配置
        val option = M3U8VodOption()
//        option.setUseDefConvert(false)
        if (vodTsUrlConverter == null){
            vodTsUrlConverter = VodTsUrlConverter();
        }
        // 不使用默认的url转换
        option.setVodTsUrlConvert(vodTsUrlConverter)
        //忽略下载失败的ts切片，即使有失败的切片，下载完成后也要合并所有切片，并进入complete回调
        option.ignoreFailureTs()
        mTaskId = Aria.download(this) // 设置点播文件下载地址
            .load(mUrl)
            .setFilePath(FilePath, true) // 设置点播文件保存路径
            .m3u8VodOption(option) // 调整下载模式为m3u8点播
            .create()
    }

    /**
     * @desc 使用现有的进程下载
     * @author QZL
     * @time 2021/3/13 17:12
     */
    fun startOther(url: String?, FilePath: String?): Long {
        mUrl = url
        mTaskId = Aria.download(this) // 设置点播文件下载地址
                .load(mUrl)
                .setFilePath(FilePath, true) // 设置点播文件保存路径
                .create()
        return mTaskId
    }

    //点播地址转换
    class VodTsUrlConverter : IVodTsUrlConverter {
        override fun convert(m3u8Url: String, tsUrls: List<String>): List<String> {
            // 转换ts文件的url地址
//            Uri uri = Uri.parse(m3u8Url);
//            String parentUrl = "http://" + uri.getHost();
//            val index = m3u8Url.lastIndexOf("/")
//            val parentUrl = m3u8Url.substring(0, index + 1)
//            val newUrls: MutableList<String> = ArrayList()
//            for (url in tsUrls) {
//                newUrls.add(parentUrl + url)
//            }
//            return newUrls // 返回有效的ts文件url集合

            // 转换ts文件的url地址
            val uri = Uri.parse(m3u8Url)
            //val parentUrl = "http://" + uri.host + ":8082/"
            val parentUrl = m3u8Url.substring(0,m3u8Url.indexOf(uri.path!!)+1)
            val newUrls: MutableList<String> = ArrayList()
            for (url in tsUrls) {
                if (!url.startsWith("http://") || !url.startsWith("https://")) {
                    newUrls.add((parentUrl + url).replace("//", "/"))
                } else {
                    newUrls.add(url)
                }
            }
            return newUrls // 返回有效的ts文件url集合
        }
    }

    //直播地址转换
    private class LiveTsUrlConverter : ILiveTsUrlConverter {
        override fun convert(m3u8Url: String, tsUrl: String): String {
            val index = m3u8Url.lastIndexOf("/")
            val parentUrl = m3u8Url.substring(0, index + 1)
            return parentUrl + tsUrl // 组成有效的下载地址
        }
    }

    //恢复任务
    fun resume(){
        Aria.download(this).load(mTaskId).resume()
    }

    fun reStart(){
        Aria.download(this).load(mTaskId).reStart()
    }

    fun stop() {
        Aria.download(this).load(mTaskId).stop()
    }

    fun cancel() {
        Aria.download(this).load(mTaskId).cancel()
    }

    fun unRegister() {
        Aria.download(this).unRegister()
    }

    init {
        Aria.download(this).register()
        mContext = context
        mView = view
    }
}