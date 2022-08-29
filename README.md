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
lastestVersion = [![](https://jitpack.io/v/qiangzhouliang/QaPicker.svg)](https://jitpack.io/#qiangzhouliang/download)
~~~
dependencies {
	  implementation 'com.github.qiangzhouliang:download:$lastestVersion'
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
- 3 自定义下载
```
//点播地址转换
    private class VodTsUrlConverter implements IVodTsUrlConverter {
        @Override
        public List<String> convert(String m3u8Url, List<String> tsUrls) {
            // 转换ts文件的url地址
            Uri uri = Uri.parse(m3u8Url);
            String parentUrl = "http://" + uri.getHost()+":8082/";
            List<String> newUrls = new ArrayList<>();
            for (String url : tsUrls) {
                newUrls.add((parentUrl + url).replace("//","/"));
            }

            return newUrls; // 返回有效的ts文件url集合
        }
    }
    
    QDownloadAnyModule anyRunnModule = new QDownloadAnyModule(this,view);
            //"http://hcjs2ra2rytd8v8np1q.exp.bcevod.com/mda-hegtjx8n5e8jt9zv/mda-hegtjx8n5e8jt9zv.m3u8"
//            anyRunnModule.start(item.getPlayURL(), FileUtils.getPath("download").toString() + "/test.mp4");
            // 创建m3u8直播文件配置
            M3U8VodOption option = new M3U8VodOption();
            option.setVodTsUrlConvert(new VodTsUrlConverter());
            //忽略下载失败的ts切片，即使有失败的切片，下载完成后也要合并所有切片，并进入complete回调
            option.ignoreFailureTs();
            HttpBuilderTarget download = Aria.download(this) // 设置点播文件下载地址
                    .load(item.getPlayURL())
                    .setFilePath(FileUtils.getPath("download").toString() + "/test.mp4", true) // 设置点播文件保存路径
                    .m3u8VodOption(option);

            anyRunnModule.start(download);

            anyRunnModule.setOnRunning((downloadTask, view1) -> {
                ((TasksCompletedView) view1).setProgress(downloadTask.getPercent());
            });
            anyRunnModule.setOnComplate((task, view12) -> {
                System.out.println("下载完成");
                ((TasksCompletedView) view12).setProgress(task.getPercent());
            });
```
# 3 版本更新说明
## 3.1 1.0.0 版本
做了m3u8文件下载分装
## 3.2 1.0.1 版本
新增了全局配置
### 3.3 1.0.2
设置点播文件自定义



