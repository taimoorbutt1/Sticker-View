package com.example.stickerview;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;

import androidx.annotation.NonNull;


public abstract class Sticker {

    private boolean init;

    private Matrix matrix;

    private float[] srcPts;

    private float[] dst;

    private float[] rotateSrcPts;

    private Path boundPath;

    private float minStickerSize;


    public void init(int width, int height) {
        matrix = new Matrix();

        srcPts = new float[]{0, 0,                                    // 左上
                width, 0,                              // 右上
                width, height,                // 右下
                0, height};

        rotateSrcPts = new float[]{
                width / 2, height / 2,
                width, height,
        };
        dst = new float[8];
        boundPath = new Path();
    }

    public abstract void draw(@NonNull Canvas canvas);

    public abstract int getWidth();

    public abstract int getHeight();

    public float getMinStickerSize() {
        return minStickerSize;
    }

    public void setMinStickerSize(float minStickerSize) {
        this.minStickerSize = minStickerSize;
    }

    boolean isInit() {
        return init;
    }

    void setInit(boolean init) {
        this.init = init;
    }

    Matrix getMatrix() {
        return matrix;
    }

    float getBitmapScale() {
        return getWidth() / (float) getHeight();
    }

    float[] getDst() {
        return dst;
    }

    float[] getRotateSrcPts() {
        return rotateSrcPts;
    }

    void converse() {
        matrix.mapPoints(dst, srcPts);
    }

    Path getBoundPath() {
        boundPath.reset();
        boundPath.moveTo(dst[0], dst[1]);
        boundPath.lineTo(dst[2], dst[3]);
        boundPath.lineTo(dst[4], dst[5]);
        boundPath.lineTo(dst[6], dst[7]);
        boundPath.lineTo(dst[0], dst[1]);
        return boundPath;
    }
}
