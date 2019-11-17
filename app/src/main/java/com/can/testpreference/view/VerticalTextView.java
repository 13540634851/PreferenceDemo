package com.can.testpreference.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.HashMap;

public class VerticalTextView extends View {
    private Paint mPaint;
    private Paint mDebugPaint;
    private float mSpacing;
    private String mText;
    private boolean mIsHorizontal = false;
    private Paint.FontMetrics mFontMetrics;
    private String[] mDrawText;
    private float mWordHeight;
    private float mVerticalwordHeight;
    private int mGravity = Gravity.START;
    private int mViewWidth, mViewHeight;
    private HashMap<Integer, Integer> mLengthRecord;
    private float mChineseWordWdth = -1;
    private static final boolean DEBUG = true;
    private int[] mLinearGradientColor;
    private int mAngle;

    public VerticalTextView(Context context) {
        super(context);
        init();
    }

    public VerticalTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerticalTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VerticalTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs);
        init();
    }

    //设置颜色渐变
    public void setLinearGradientColor(int[] color, int angle) {
        mLinearGradientColor = color;
        mAngle = angle;
        update();
    }

    //设置字符串
    public void setText(String text) {
        this.mText = text;
        update();
    }

    //设置对齐方式
    public void setGravity(int mGravity) {
        this.mGravity = mGravity;
        update();
    }

    //设置显示方式
    public void setHorizontal(boolean orientation) {
        this.mIsHorizontal = orientation;
        update();
    }

    public boolean isHorizontal() {
        return mIsHorizontal;
    }

    public void update() {
        requestLayout();
        invalidate();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setTextSize(60);
        mPaint.setColor(Color.RED);

        mDebugPaint = new Paint();
        mDebugPaint.setStyle(Paint.Style.STROKE);
        mDebugPaint.setColor(Color.GRAY);
    }

    //根据水平和垂直显示测量视图大小，特别是warp_content
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!TextUtils.isEmpty(mText)) {
            mDrawText = mText.split("\n");
        }

        mFontMetrics = mPaint.getFontMetrics();
        mWordHeight = mFontMetrics.descent - mFontMetrics.ascent;
        mVerticalwordHeight = mFontMetrics.descent - mFontMetrics.ascent;
        mSpacing = 0;

        if (mIsHorizontal) {
            measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        } else {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    //水平测量
    public void measureHorizontal(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        mViewHeight = heightSpecSize;
        mViewWidth = widthSpecSize;

        int lineCount = 1;
        if (mDrawText != null) {
            lineCount = Math.max(1, mDrawText.length);
        }

        if (widthSpecMode == MeasureSpec.AT_MOST) {
            mViewWidth = 0;
            float maxHeight = 0;
            if (!TextUtils.isEmpty(mText)) {
                for (String dtxt : mDrawText) {
                    float height = mPaint.measureText(dtxt);
                    if (maxHeight < height) {
                        maxHeight = height;
                    }
                }
                maxHeight += 2 * (mSpacing);
            }
            mViewWidth += maxHeight;
        }

        if (heightSpecMode == MeasureSpec.AT_MOST) {
            mViewHeight = (int) ((mWordHeight + mSpacing) * lineCount + mSpacing);
        }
        if (widthSpecSize < mViewWidth) {
            mViewWidth = widthSpecSize;
        }
        if (heightSpecSize < mViewHeight) {
            mViewHeight = heightSpecSize;
        }
    }

    //垂直测量
    public void measureVertical(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        mViewHeight = heightSpecSize;
        mViewWidth = widthSpecSize;

        if (mLengthRecord == null) {
            mLengthRecord = new HashMap<>();
        }
        if (widthSpecMode == MeasureSpec.AT_MOST) {
            int widthLine = 1;
            mViewWidth = 0;
            if (mDrawText != null && mDrawText.length > 0) {
                widthLine = mDrawText.length;
            }
            mViewWidth = (int) (mWordHeight * widthLine + mSpacing);
        }

        mChineseWordWdth = -1;
        mViewHeight = 0;
        int maxHeight = 0;
        if (mDrawText != null && mDrawText.length > 0) {
            ChineseWordsWatch chineseWordsWatch = new ChineseWordsWatch();
            for (int i = mDrawText.length - 1; i >= 0; i--) {
                chineseWordsWatch.setString(mDrawText[i]);
                int height = 0;
                String readStr;
                while (null != (readStr = chineseWordsWatch.nextString())) {
                    if (chineseWordsWatch.isChineseWord()) {
                        if (mChineseWordWdth == -1) {
                            mChineseWordWdth = mPaint.measureText(readStr);
                        }
                        height += mVerticalwordHeight;
                    } else {
                        height += mPaint.measureText(readStr);
                    }
                }
                mLengthRecord.put(i, height);
                if (maxHeight < height) {
                    maxHeight = height;
                }
            }
        }

        if (mChineseWordWdth != -1) {
            mViewWidth = (int) (mViewWidth - (mWordHeight - mChineseWordWdth));
        }


        if (heightSpecMode == MeasureSpec.AT_MOST) {
            mViewHeight = (int) (maxHeight + 2 * mSpacing);
        }

        if (widthSpecSize < mViewWidth) {
            mViewWidth = widthSpecSize;
        }
        if (heightSpecSize < mViewHeight) {
            mViewHeight = heightSpecSize;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (TextUtils.isEmpty(mText)) {
            return;
        }
        if (mIsHorizontal) {
            drawHorizontal(canvas);
        } else {
            drawVertical(canvas);
        }
    }

    //垂直绘制
    private void drawVertical(Canvas canvas) {
        int startX = (int) mSpacing;
        int startY = (int) mSpacing;

        ChineseWordsWatch chineseWordsWatch = new ChineseWordsWatch();
        for (int i = mDrawText.length - 1; i >= 0; i--) {
            chineseWordsWatch.setString(mDrawText[i]);
            String readStr;
            int currentheight = startY;
            if (mLengthRecord != null && mLengthRecord.containsKey(i)) {
                int popHight = mLengthRecord.get(i);
                if (mGravity == Gravity.CENTER) {
                    currentheight = (mViewHeight - popHight) >> 1;
                } else if (mGravity == Gravity.END) {
                    currentheight = (int) (mViewHeight - 2 * mSpacing - popHight);
                } else {
                    currentheight = startY;
                }
            }

            LinearGradient baseLinearGradient = null;
            if (mLinearGradientColor != null) {
                baseLinearGradient = LinearGradientTool.getLinearGradient(0, 0, mViewWidth, mViewHeight, mLinearGradientColor, mAngle);
            }


            while (null != (readStr = chineseWordsWatch.nextString())) {
                if (chineseWordsWatch.isChineseWord()) {
                    currentheight += mVerticalwordHeight;
                    if (DEBUG) {
                        drawDebugLine(canvas, startX, currentheight - mFontMetrics.descent, (int) mPaint.measureText(readStr));
                    }

                    if (baseLinearGradient != null) {
                        mPaint.setShader(baseLinearGradient);
                    }
                    canvas.drawText(readStr, startX, currentheight - mFontMetrics.descent, mPaint);
                } else {
                    float marginFixed = 0;
                    if (mChineseWordWdth != -1) {
                        marginFixed = (float) ((mVerticalwordHeight - mChineseWordWdth) * 0.5);
                    }
                    canvas.save();
                    canvas.rotate(90, startX, currentheight);

                    if (DEBUG) {
                        drawDebugLine(canvas, startX, currentheight - mFontMetrics.descent + marginFixed, (int) mPaint.measureText(readStr));
                    }


                    if (mLinearGradientColor != null) {
                        mPaint.setShader(LinearGradientTool.getRotate90LinearGradient(startX, currentheight,
                                0, 0,
                                mViewWidth, mViewHeight,
                                mLinearGradientColor, mAngle));
                    }

                    canvas.drawText(readStr, startX, currentheight - mFontMetrics.descent + marginFixed, mPaint);
                    canvas.restore();
                    currentheight += mPaint.measureText(readStr);
                }
            }
            startX += mWordHeight;
        }
    }

    //水平绘制
    private void drawHorizontal(Canvas canvas) {
        int startX;
        int startY = (int) (mSpacing - mFontMetrics.ascent);


        if (mLinearGradientColor != null) {
            mPaint.setShader(LinearGradientTool.getLinearGradient(0, 0, mViewWidth, mViewHeight, mLinearGradientColor, mAngle));
        }
        for (String dtxt : mDrawText) {
            float textWidth = mPaint.measureText(dtxt);
            if (mGravity == Gravity.START) {
                startX = (int) mSpacing;
            } else if (mGravity == Gravity.END) {
                startX = (int) (mViewWidth - mSpacing - textWidth);
            } else {
                startX = ((int) (mViewWidth - textWidth)) >> 1;
            }

            canvas.drawText(dtxt, startX, startY, mPaint);
            if (DEBUG) {
                drawDebugLine(canvas, startX, startY, (int) mPaint.measureText(dtxt));
            }
            startY += mWordHeight + mSpacing;
        }
    }

    private void drawDebugLine(Canvas canvas, float offx, float offy, float length) {
        canvas.drawRect(offx, offy + mFontMetrics.ascent, offx + length, offy + mFontMetrics.descent, mDebugPaint);
    }
}
