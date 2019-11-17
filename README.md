# 能够竖排和横排显示的控件

1.增加颜色渐变
2.设置了文本显示边框，去掉设置DEBUG=false
3.修改了文本区域大小不正确的问题
4.修改了居中，居右不正确的问题
5.修改竖排中英文混合显示中英文对齐方式，目前竖中心线对齐，底线对齐可以注释掉marginFixed = (float) ((mVerticalwordHeight - mChineseWordWdth) * 0.5);，使marginFixed 恒为 0；
6.修改了文本区域宽或高会超过外部layout布局的错误
7.行间距默认为0，如果需要修改请自行设置mSpacing

# 预览
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191117221011914.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3cxNzY0NjYyNTQz,size_16,color_FFFFFF,t_70)

