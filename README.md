# EasyFrame
  EasyFrame框架整合了日常开发常用的控件(圆形(圆角)imageview,显示异常对话框,等待对话框,加载进度条等...)与功能类集合.包含:下拉刷新,上拉加载,6.0权限适配,app自动更新,RxJava与Retrofit 2.0的封装等众多新技术.

Usage Method
--------
Git
```groovy
https://github.com/ZyElite/EasyFrame.git
```
or Gradle:
```groovy
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

```groovy
   dependencies {
	         compile 'com.github.ZyElite:EasyFrame:v1.0'
	}
```
#Example
###MaterialRefreshLayout(上下拉刷新万能控件MD风格)
```groovy
   <cn.zy.ef.refresh.MaterialRefreshLayout xmlns:android="http://schemas.android.com/apk/res/android"
   	xmlns:ef="http://schemas.android.com/apk/res-auto"
   	android:id="@+id/refresh"
    	android:layout_width="match_parent"
    	android:layout_height="match_parent"
    	ef:isLoadMore="true"  //是否加载更多
    	ef:overlay="true"     //是否覆盖
    	ef:progress_colors="@array/material_colors"    //进度条颜色
    	ef:progress_size_type="normal"  
    	ef:wave_color="#90ffffff"   //是否显示下拉波纹颜色
    	ef:wave_height_type="normal"  //波纹高度
    	ef:wave_show="true">   //是否显示下拉波纹
    		<ListView
        	     android:layout_width="match_parent"
        	     android:layout_height="match_parent" />

    </cn.zy.ef.refresh.MaterialRefreshLayout>
```
