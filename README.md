# 能够竖排和横排显示的控件

1.增加颜色渐变
2.设置了文本显示边框，去掉设置DEBUG=false
3.修改了文本区域大小不正确的问题
4.修改了居中，居右不正确的问题
5.修改竖排中英文混合显示中英文对齐方式，目前竖中心线对齐，底线对齐可以注释掉marginFixed = (float) ((mVerticalwordHeight - mChineseWordWdth) * 0.5);，使marginFixed 恒为 0；
6.修改了文本区域宽或高会超过外部layout布局的错误
7.行间距默认为0，如果需要修改请自行设置mSpacing

# 预览
![在这里插入图片描述](https://github.com/13540634851/PreferenceDemo/blob/master/capture/%E6%B8%90%E5%8F%98%E7%BA%B5%E5%90%91%E4%B8%AD%E8%8B%B1%E6%96%87%E6%B7%B7%E5%90%88.png,size_16,color_FFFFFF,t_70)

