package com.qzl.qdownload.intf

import android.view.View
import com.arialyy.aria.core.task.DownloadTask

/**
 * @desc 文件下载接口调用
 * @author qiangzhouliang
 * @email 2538096489@qq.com
 * @time 2021/3/13 16:17
 * @class Download
 * @package com.qzl.qdownload
 */
interface QDownloadIntfResume {
    fun onResume(task: DownloadTask?,view: View?)
}