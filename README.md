# download
一行代码实现文件下载，支持正常格式文件下载，也支持m3u8格式文件下载
使用地址：https://jitpack.io/#qiangzhouliang/download/1.0.0
Aria使用文档：https://aria.laoyuyu.me/aria_doc/download/m3u8_vod.html
# 1 如何引入自己的项目
## 1.1 将JitPack存储库添加到您的构建文件中
将其添加到存储库末尾的root（项目） build.gradle中：
~~~
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
~~~
## 1.2 添加依赖项
~~~
dependencies {
	  implementation 'com.github.qiangzhouliang:download:1.0.0'
}
~~~
# 2 如何使用
## 2.1 在application中注册
~~~
QDownloadConfig.init(this)
~~~
## 2.2 在要使用的地方写上如下代码
- 1 java 代码中
~~~
QDownloadAnyModule anyRunnModule = new QDownloadAnyModule(this,findViewById(R.id.tv));
        anyRunnModule.start("http://hcjs2ra2rytd8v8np1q.exp.bcevod.com/mda-hegtjx8n5e8jt9zv/mda-hegtjx8n5e8jt9zv.m3u8", FileUtils.getPath("download").toString() + "/test.mp4");
        anyRunnModule.setOnComplate(new QDownloadIntfComplate() {
            @Override
            public void onComplate(@Nullable DownloadTask task, @Nullable View view) {
                ((TextView)view).setText(task.getFilePath()+"下载完成");
            }
        });
~~~
- 2 kotlin代码中
```
val anyRunnModule = QDownloadAnyModule(this)
            anyRunnModule.start("http://hcjs2ra2rytd8v8np1q.exp.bcevod.com/mda-hegtjx8n5e8jt9zv/mda-hegtjx8n5e8jt9zv.m3u8", FileUtils.getPath("download").toString() + "/test.mp4");
            anyRunnModule.onComplate = object :QDownloadIntfComplate{
                override fun onComplate(task: DownloadTask?,view:View?) {
                    findViewById<TextView>(R.id.tv).setText(task?.downloadEntity?.filePath+"下载完成")
                }
            }
```
# 3 版本更新说明
## 3.1 1.0.0 版本
做了m3u8文件下载分装
## 3.2 1.0.1 版本
新增了全局配置




