##Utils个人自用的一个工具包。
##imageLoader
###**支持加载网络和本地图片，加载网络图片使用了双缓存机制和libjpeg类库对图片进行处理，压缩比率可自由设置，加载本地图片使用了内存缓存机制，基本满足日常使用。**
###基本用法：
1.缓存：

- `ImageCache mCache=new MemeryCache();`
- `ImageCache mCache=new Diskache("fileName",60);//60:压缩比率`
- `ImageCache mCache=new DoubleCache("fileName",60);`

2.实例化ImageLoader

    ImageLoader imageLoader=ImageLoader.getInstance(mCache,true);//true:是否通过网络加载

3.加载图片

    imageLoader.load("path",targetView);

4.添加回调

    imageLoader.callBack(new CallBack() {
           @Override
           public void start() {
               
           }

           @Override
           public void loading() {

           }

           @Override
           public void success() {

           }

           @Override
           public void error(int code) {

           }
       });
	
5.链式调用：

    ImageLoader.getInstance(new DoubleCache("fileName",60),false).load("path", (ImageView)
               holder.setView(R.id.iv_item_gallery_rv)).callBack(new CallBack() {
           @Override
           public void start() {

           }

           @Override
           public void loading() {

           }

           @Override
           public void success() {

           }

           @Override
           public void error(int code) {
			//code:responseCode
           }
       });

##额外福利：

libjpeg的使用：

- 导入imageLoader Library库中的so库和`android-jpeg.jar`

- 压缩bitmap:
`File file=new File("sdcard/"+cacheDir,MD5Utils.getMd5(path));`
`//将图片处理后保存到本地` 
`//第一个参数是获取到的bitmap对象，`
`//第二个参数是压缩比率`
`//第三个参数是保存图片的路径`
`//第四个参数是是否采用哈夫曼算法（jpeg的核心算法，`
`//即谷歌在使用skia引擎的时候所阉割的，也是苹果处理图片和安卓处理`
`//图片的区别所在）`
    `NativeUtil.compressBitmap(bitmap,PERCENT,file.getAbsolutePath(),true)`;
###最后宣传下自己的博客中使用libjpeg的方法：

> http://blog.csdn.net/qq_16070781/article/details/54288269

#*喜欢的请点start,祝您步步高升。*