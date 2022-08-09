# view-puzzle
view-puzzle 简单滑块验证
代码简单好用可以自定义改造

#自定义比例
setDimensionRatio("668:300");
#设置图片
mBinding.slidePuzzle.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.drag_cover_b));
#验证监听
mBinding.slidePuzzle.setOnVerifyListener