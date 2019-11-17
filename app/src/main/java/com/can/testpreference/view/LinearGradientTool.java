package com.can.testpreference.view;

import android.graphics.LinearGradient;
import android.graphics.Shader;

public class LinearGradientTool {


    /***
     * 根据旋转90度渐变区域和渐变角度生产一个渐变器
     * @param rotateX 旋转中心横坐标
     * @param rotateY 旋转中心纵坐标
     * @param x0      渐变起始横坐标
     * @param y0      渐变结束纵坐标
     * @param x1      渐变结束横坐标
     * @param y1      渐变起始纵坐标
     * @param color   渐变颜色数组  至少两种颜色
     * @param angle   渐变角度  0~360
     * @return 根据旋转渐变区域和渐变角度生产一个渐变器
     */
    public static LinearGradient getRotate90LinearGradient(float rotateX, float rotateY, float x0, float y0, float x1, float y1, int[] color, int angle) {


        float width = x1 - x0;
        float height = y1 - y0;
        float colorstartX = rotateX - rotateY;
        float colorStarty = (rotateY + rotateX) - width;

        return getLinearGradient(colorstartX
                , colorStarty
                , colorstartX + height
                , colorStarty + width
                , color, angle + 90);
    }

    /**
     * 根据渐变区域和渐变角度生产一个渐变器
     *
     * @param x0    渐变起始横坐标
     * @param y0    渐变结束纵坐标
     * @param x1    渐变结束横坐标
     * @param y1    渐变起始纵坐标
     * @param color 渐变颜色数组  至少两种颜色
     * @param angle 渐变角度  0~360
     *              <p>
     *              <p>
     *              angle角度为例
     *              （x0,y0）           渐变结束
     *              -----------------
     *              |              /|
     *              |             / |
     *              |            /  |
     *              |           /   |
     *              |          /    |
     *              |         /     |
     *              |        /      |
     *              |       /       |
     *              |      /        |
     *              |     /         |
     *              |    /          |
     *              |   /           |
     *              |  /            |
     *              | / \ angle     |
     *              |/  )           |
     *              -----------------
     *              渐变开始         (x1,y1)
     * @return 生产一个渐变器
     */
    public static LinearGradient getLinearGradient(float x0, float y0, float x1, float y1, int[] color, int angle) {
        if (angle > 0) {
            angle = angle % 360;
        } else if (angle < 0) {
            angle = 360 + angle % 360;
        }

        float centerX = 0.5f * (x0 + x1);
        float centerY = 0.5f * (y0 + y1);
        float radius = (float) (Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - x0) * (y1 - x0)) * 0.5);
        float offx = (float) (radius * Math.cos(angle * Math.PI / 180));
        float offY = (float) (radius * Math.sin(angle * Math.PI / 180));

        int colorLen = color.length;
        float[] postion = new float[colorLen];
        postion[0] = 0;
        postion[colorLen - 1] = 1;
        float dx = 1.0f / (colorLen - 1);
        for (int i = 1; i < colorLen - 1; i++) {
            postion[i] = i * dx;
        }

        LinearGradient linearGradient = new LinearGradient(centerX - offx,
                centerY + offY,
                centerX + offx,
                centerY - offY,
                color,
                postion,
                Shader.TileMode.CLAMP);
        return linearGradient;
    }
}
