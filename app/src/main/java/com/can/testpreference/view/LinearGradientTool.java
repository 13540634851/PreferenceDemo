package com.can.testpreference.view;

import android.graphics.LinearGradient;
import android.graphics.Shader;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LinearGradientTool {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({L_R, LB_TR, B_T, RB_TL, R_L, TR_LB, T_B, TL_RB})
    public @interface Duration {
    }


    public static final int L_R = 0;
    public static final int LB_TR = 45;
    public static final int B_T = 90;
    public static final int RB_TL = 135;
    public static final int R_L = 180;
    public static final int TR_LB = 225;
    public static final int T_B = 270;
    public static final int TL_RB = 315;


    /***
     * 根据旋转90度渐变区域和渐变角度生产一个渐变器
     * @param rotateX 旋转中心横坐标
     * @param rotateY 旋转中心纵坐标
     * @param x0      渐变起始横坐标
     * @param y0      渐变结束纵坐标
     * @param x1      渐变结束横坐标
     * @param y1      渐变起始纵坐标
     * @param color   渐变颜色数组  至少两种颜色
     * @param angle   渐变角度  0~360：角度并非真实角度，把长宽看成正方形的角度，实际多数情况不是正方形
     * @return 根据旋转渐变区域和渐变角度生产一个渐变器
     */
    public static LinearGradient getRotate90LinearGradient(float rotateX, float rotateY, float x0, float y0, float x1, float y1, int[] color, @Duration int angle) {
        float width = x1 - x0;
        float height = y1 - y0;
        float colorstartX = rotateX - rotateY;
        float colorStarty = (rotateY + rotateX) - width;
        angle = angle + 90;
        if (angle > 0) {
            angle = angle % 360;
        }

        return getLinearGradient(colorstartX
                , colorStarty
                , colorstartX + height
                , colorStarty + width
                , color, angle);
    }

    /**
     * 根据渐变区域和渐变角度生产一个渐变器
     *
     * @param x0    渐变起始横坐标
     * @param y0    渐变结束纵坐标
     * @param x1    渐变结束横坐标
     * @param y1    渐变起始纵坐标
     * @param color 渐变颜色数组  至少两种颜色
     * @param angle 渐变角度  0~360：角度并非真实角度，把长宽看成正方形的角度，实际多数情况不是正方形
     *              <p>
     *              <p>
     *              angle = 45 角度为例，注意这里的角度并非真实角度，把长宽看成正方形的角度，实际多数情况不是正方形
     *              （x0,y0）           (x1,y0)
     *              -----------------
     *              |              /| 渐变结束
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
     *              | / \ angle = 45|
     *              |/  )           |
     *        渐变开始-----------------
     *              (x0,y1)          (x1,y1)
     *
     *
     *
     * L_R, LB_TR, B_T, RB_TL, R_L, TR_LB, T_B, TL_RB
     * -----------------------------------
     * |\               |               /|
     * | \              |              / |
     * |  \             |             /  |
     * |   \            | B_T        /   |
     * |    \           |           /    |
     * |     \          |          /     |
     * |      \         |         /      |
     * |       \        |        /       |
     * |        \       |       /        |
     * |         \      |      /         |
     * |     RB_TL\     |     / LB_TR    |
     * |           \    |    /           |
     * |            \   |   /            |
     * |             \  |  /             |
     * |              \ | /              |
     * |      R_L      \|/       L_R     |
     * |----------------|----------------|
     * |               /|\               |
     * |              / | \              |
     * |             /  |  \             |
     * |            /   |   \            |
     * |           /    |    \           |
     * |          /     |     \          |
     * |    TR_LB/      |      \TL_RB    |
     * |        /       |       \        |
     * |       /        |        \       |
     * |      /         |         \      |
     * |     /          |          \     |
     * |    /      T_B  |           \    |
     * |   /            |            \   |
     * |  /             |             \  |
     * | /              |              \ |
     * |/               |               \|
     * -----------------------------------
     *
     *
     * @return 生产一个渐变器
     */
    public static LinearGradient getLinearGradient(float x0, float y0, float x1, float y1, int[] color, @Duration int angle) {
        float startX = 0, startY = 0, stopX = 0, stopY = 0;
        switch (angle) {
            case LB_TR:
                startX = x0;
                startY = y1;
                stopX = x1;
                stopY = y0;
                break;
            case B_T:
                startX = x0;
                startY = y1;
                stopX = x0;
                stopY = y0;
                break;
            case RB_TL:
                startX = x1;
                startY = y1;
                stopX = x0;
                stopY = y0;
                break;
            case R_L:
                startX = x1;
                startY = y0;
                stopX = x0;
                stopY = y0;
                break;
            case TR_LB:
                startX = x1;
                startY = y0;
                stopX = x0;
                stopY = y1;
                break;
            case T_B:
                startX = x0;
                startY = y0;
                stopX = x0;
                stopY = y1;
                break;
            case TL_RB:
                startX = x0;
                startY = y0;
                stopX = x1;
                stopY = y1;
                break;
            default:
                //case L_R:
                startX = x0;
                startY = y0;
                stopX = x1;
                stopY = y0;
        }


        int colorLen = color.length;
        float[] postion = new float[colorLen];
        postion[0] = 0;
        postion[colorLen - 1] = 1;
        float dx = 1.0f / (colorLen - 1);
        for (int i = 1; i < colorLen - 1; i++) {
            postion[i] = i * dx;
        }

        LinearGradient linearGradient = new LinearGradient(
                startX, startY,
                stopX, stopY,
                color,
                postion,
                Shader.TileMode.CLAMP);
        return linearGradient;
    }
}
