package com.can.testpreference.view;

public class ChineseWordsWatch {
    private char[] mCharArray;
    private int mStartX, mStopX;
    private boolean mIsChinese;

    public ChineseWordsWatch() {

    }

    public void setString(String doString) {
        this.mCharArray = doString.toCharArray();
        mStartX = 0;
        mStopX = 0;
    }

    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;
    }

    public String nextString() {
        mStopX = mStartX;
        mIsChinese = false;
        boolean containNonChineseWord = false;
        while (mStopX < mCharArray.length) {
            if (isChinese(mCharArray[mStopX])) {
                if (containNonChineseWord) {
                    mIsChinese = false;
                } else {
                    mStopX++;
                    mIsChinese = true;
                }
                break;
            } else {
                containNonChineseWord = true;
                mStopX++;
            }
        }

        String ret;
        if (mStartX == mStopX) {
            ret = null;
        } else {
            ret = new String(mCharArray, mStartX, mStopX - mStartX);
        }
        mStartX = mStopX;
        return ret;
    }

    public boolean isChineseWord() {
        return mIsChinese;
    }
}
