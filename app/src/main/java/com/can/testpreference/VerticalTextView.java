package com.can.testpreference;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.HashMap;

public class VerticalTextView extends View {
    private Paint paint;
    private float dvide;
    private String text;
    private boolean isHorizontal = false;
    private Paint.FontMetrics fontMetrics;
    private String[] drawText;
    private float wordHeight;
    private float verticalwordHeight;
    private int gravity = Gravity.START;
    private int viewWidth, viewHeight;
    private HashMap<Integer, Integer> lengthRecord;
    private boolean hasMeasure = false;

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

    private void init() {
        paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(Color.RED);
    }

    public void setText(String text) {
        this.text = text;
        update();
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
        update();
    }

    public void setHorizontal(boolean orientation) {
        this.isHorizontal = orientation;
        update();
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void update() {
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!TextUtils.isEmpty(text)) {
            drawText = text.split("\n");
        }

        fontMetrics = paint.getFontMetrics();
        wordHeight = fontMetrics.bottom - fontMetrics.top;
        verticalwordHeight = -fontMetrics.ascent;
        dvide = wordHeight / 5;

        if (isHorizontal) {
            measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        } else {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        }
        hasMeasure = true;
        setMeasuredDimension(viewWidth, viewHeight);
    }

    public void measureHorizontal(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        viewHeight = heightSpecSize;
        viewWidth = widthSpecSize;

        int lineCount = 1;
        if (drawText != null) {
            lineCount = Math.max(1, drawText.length);
        }

        if (widthSpecMode == MeasureSpec.AT_MOST) {
            viewWidth = 0;
            float maxHeight = 0;
            if (!TextUtils.isEmpty(text)) {
                for (String dtxt : drawText) {
                    float height = paint.measureText(dtxt);
                    if (maxHeight < height) {
                        maxHeight = height;
                    }
                }
                maxHeight += 2 * (dvide);
            }
            viewWidth += maxHeight;
        }

        if (heightSpecMode == MeasureSpec.AT_MOST) {
            viewHeight = (int) ((wordHeight + dvide) * lineCount + dvide);
        }
    }

    public void measureVertical(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        viewHeight = heightSpecSize;
        viewWidth = widthSpecSize;

        if (lengthRecord == null) {
            lengthRecord = new HashMap<>();
        }
        if (widthSpecMode == MeasureSpec.AT_MOST) {
            int widthLine = 1;
            viewWidth = 0;
            if (drawText != null && drawText.length > 0) {
                widthLine = drawText.length;
            }
            viewWidth = (int) (wordHeight * widthLine + dvide);
        }


        viewHeight = 0;
        int maxHeight = 0;
        if (drawText != null && drawText.length > 0) {
            ChineseWordsWatch chineseWordsWatch = new ChineseWordsWatch();
            for (int i = drawText.length - 1; i >= 0; i--) {
                chineseWordsWatch.setString(drawText[i]);
                int height = 0;
                String readStr;
                while (null != (readStr = chineseWordsWatch.nextString())) {
                    if (chineseWordsWatch.isChinese) {
                        height += verticalwordHeight;
                    } else {
                        float marginFixed = fontMetrics.ascent - fontMetrics.top;
                        height += marginFixed + paint.measureText(readStr);
                    }
                }
                Log.i("wangcan", "add " + height);
                lengthRecord.put(i, height);
                if (maxHeight < height) {
                    maxHeight = height;
                }
            }
        }

        if (heightSpecMode == MeasureSpec.AT_MOST) {
            viewHeight = (int) (maxHeight + 3 * dvide);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (isHorizontal) {
            drawHorizontal(canvas);
        } else {
            drawVertical(canvas);
        }
    }

    private void drawVertical(Canvas canvas) {
        int startX = (int) dvide;
        int startY = (int) dvide;


        ChineseWordsWatch chineseWordsWatch = new ChineseWordsWatch();
        for (int i = drawText.length - 1; i >= 0; i--) {
            chineseWordsWatch.setString(drawText[i]);
            String readStr;
            int currentheight = startY;
            if (lengthRecord != null && lengthRecord.containsKey(i)) {
                int popHight = lengthRecord.get(i);
                if (gravity == Gravity.CENTER) {
                    currentheight = (viewHeight - popHight) >> 1;
                } else if (gravity == Gravity.END) {
                    currentheight = (int) (viewHeight - 2 * dvide - popHight);
                } else {
                    currentheight = startY;
                }
            }

            while (null != (readStr = chineseWordsWatch.nextString())) {
                if (chineseWordsWatch.isChinese) {
                    currentheight += verticalwordHeight;
                    canvas.drawText(readStr, startX, currentheight, paint);
                } else {
                    float marginFixed = fontMetrics.ascent - fontMetrics.top;
                    canvas.save();
                    canvas.rotate(90, startX, currentheight);
                    canvas.drawText(readStr, startX + marginFixed, currentheight - fontMetrics.bottom, paint);
                    canvas.restore();
                    currentheight += marginFixed + paint.measureText(readStr);
                }
            }
            startX += wordHeight;
        }
    }

    private void drawHorizontal(Canvas canvas) {
        int startX;
        int startY = (int) (dvide - fontMetrics.top);
        for (String dtxt : drawText) {
            float textWidth = paint.measureText(dtxt);
            if (gravity == Gravity.START) {
                startX = (int) dvide;
            } else if (gravity == Gravity.END) {
                startX = (int) (viewWidth - dvide - textWidth);
            } else {
                startX = ((int) (viewWidth - textWidth)) >> 1;
            }
            canvas.drawText(dtxt, startX, startY, paint);
            startY += wordHeight + dvide;
        }
    }

    public static class ChineseWordsWatch {
        private char[] charArray;
        private int startX, stopX;
        private boolean isChinese;

        public ChineseWordsWatch() {

        }

        public void setString(String doString) {
            this.charArray = doString.toCharArray();
            startX = 0;
            stopX = 0;
        }

        public static boolean isChinese(char c) {
            return c >= 0x4E00 && c <= 0x9FA5;
        }

        public String nextString() {
            stopX = startX;
            isChinese = false;
            boolean containNonChineseWord = false;
            while (stopX < charArray.length) {
                if (isChinese(charArray[stopX])) {
                    if (containNonChineseWord) {
                        isChinese = false;
                    } else {
                        stopX++;
                        isChinese = true;
                    }
                    break;
                } else {
                    containNonChineseWord = true;
                    stopX++;
                }
            }

            String ret;
            if (startX == stopX) {
                ret = null;
            } else {
                ret = new String(charArray, startX, stopX - startX);
            }
            startX = stopX;
            return ret;
        }

        boolean isChineseWords() {
            return false;
        }
    }
}
