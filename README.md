## view-puzzle
view-puzzle 简单滑块验证
代码简单好用可以自定义改造

## Gradle
[![](https://jitpack.io/v/huimieni/view-puzzle.svg)](https://jitpack.io/#huimieni/view-puzzle/Tag)
```
implementation 'com.github.huimieni:view-puzzle:版本号看上面'
```
jitpack还要求在工程根目录的`build.gradle`中添加如下：
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
## 使用说明
# 自定义比例
setDimensionRatio("668:300")
# 设置图片
setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.drag_cover_b))
# 验证监听
setOnVerifyListener
