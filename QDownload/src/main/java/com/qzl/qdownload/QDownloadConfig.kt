package com.qzl.qdownload

import android.content.Context
import com.arialyy.aria.core.Aria

/**
 * @desc
 * @author qiangzhouliang
 * @email 2538096489@qq.com
 * @time 2021/3/13 17:30
 * @class Download
 * @package com.qzl.qdownload
 */
object QDownloadConfig {

    /**
     * @desc 如果在任意类中使用，需要在 application中注册
     * @author QZL
     * @time 2021/3/13 17:31
     */
    @JvmStatic
    fun init(context: Context?) {
        Aria.init(context)
    }
}