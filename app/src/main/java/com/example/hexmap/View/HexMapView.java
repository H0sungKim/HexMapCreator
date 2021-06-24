package com.example.hexmap.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

class HexBlock {
    public double x;
    public double y;
    public double z;
    public double midX;
    public double midY;
    private Path path = new Path();

    public static double blockSize;
    public static int ScreenWidth;
    public static int ScreenHeight;

    HexBlock(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.midX = ScreenWidth/2 + 3*blockSize*(y-x)/2;
        this.midY = ScreenHeight/2 - Math.sqrt(3)*blockSize*(x/2+y/2-z);

        path.moveTo((float)(this.midX-blockSize), (float)(this.midY));
        path.lineTo((float)(this.midX-blockSize/2), (float)(this.midY+Math.sqrt(3)*blockSize/2));
        path.lineTo((float)(this.midX+blockSize/2), (float)(this.midY+Math.sqrt(3)*blockSize/2));
        path.lineTo((float)(this.midX+blockSize), (float)(this.midY));
        path.lineTo((float)(this.midX+blockSize/2), (float)(this.midY-Math.sqrt(3)*blockSize/2));
        path.lineTo((float)(this.midX-blockSize/2), (float)(this.midY-Math.sqrt(3)*blockSize/2));
        path.close();


    }

    void draw(Canvas canvas, Paint paint) {
        canvas.drawPath(this.path, paint);
        //Log.d("Hosung.Kim", "midX = " + midX + ", midY = " + midY);
    }

    boolean isIn(float touchX, float touchY) {
        return Math.pow(touchX - midX, 2) + Math.pow(touchY - midY, 2) <= Math.pow(blockSize, 2);
    }

    void fill(Canvas canvas, Paint paint) {
        //canvas.drawCircle((float)this.midX, (float)this.midY, 20, paint);
        canvas.drawPath(this.path, paint);
    }

    void printInfo(Canvas canvas, Paint paint) {
        //canvas.drawRect(0, 0, ScreenWidth, ScreenHeight/30, eraser);
        canvas.drawText("(" + (int)this.x + ", " + (int)this.y +", " + (int)this.z + ")", 0, ScreenHeight/30, paint);
        //Log.d("Hosung.Kim", "(" + this.x + ", " + this.y +", " + this.z + ")");
    }

    int getDistance(float touchX, float touchY) {
        return (int)(Math.pow(touchX - midX, 2) + Math.pow(touchY - midY, 2));
    }

    @Override
    public boolean equals(@Nullable @org.jetbrains.annotations.Nullable Object obj) {
        if (!(obj instanceof HexBlock)) {
            return false;
        } else {
            if (this.x == ((HexBlock) obj).x && this.y == ((HexBlock) obj).y && this.z == ((HexBlock) obj).z) {
                return true;
            } else {
                return false;
            }
        }
    }
}

public class HexMapView extends View {

    private ArrayList<HexBlock> mArrayList = new ArrayList<HexBlock>();

    private int mScreenWidth;
    private int mScreenHeight;

    private Paint    mBlackPaint;
    private Paint    mWhitePaint;
    private Paint    mGrayPaint;
    private Paint    mPontPaint;
    private Bitmap   mBitmap;
    private Canvas   mCanvas;

    private Random   mRandom;

    public HexMapView(Context context) {
        super(context);
    }

    public HexMapView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);

        mScreenWidth   = metrics.widthPixels;
        mScreenHeight  = metrics.heightPixels;
        HexBlock.ScreenWidth = mScreenWidth;
        HexBlock.ScreenHeight = mScreenHeight;

        mBlackPaint = new Paint();
        mBlackPaint.setColor(Color.rgb(0, 0, 0));
        mBlackPaint.setStyle(Paint.Style.STROKE);
        mBlackPaint.setStrokeWidth(6);

        mPontPaint = new Paint();
        mPontPaint.setColor(Color.rgb(0, 0, 0));
        mPontPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPontPaint.setTextSize(mScreenHeight/30);

        mGrayPaint = new Paint();
        mGrayPaint.setColor(Color.rgb(127, 127, 127));
        mGrayPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mWhitePaint = new Paint();
        mWhitePaint.setColor(Color.rgb(255, 255, 255));
        mWhitePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mBitmap = Bitmap.createBitmap(metrics.widthPixels, metrics.heightPixels, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        mRandom = new Random();


        //Log.d("Hosung.Kim", mScreenWidth + ", " + mScreenHeight);

    }

    public void firstSetLst(double blockSize, int mapSize) {
        HexBlock.blockSize = blockSize;
        mArrayList.clear();

        for (int i=0; i<mapSize+1; i++) {
            for (int j=0; j<i+1; j++) {
                addInstanceInLst(i, j, 0);
                addInstanceInLst(i, 0, j);
                addInstanceInLst(j, i, 0);
                addInstanceInLst(0, i, j);
                addInstanceInLst(j, 0, i);
                addInstanceInLst(0, j, i);
            }
        }
        drawHexMap();
    }

    void drawHexMap() {
        for (int i=0; i<mArrayList.size(); i++) {
            mArrayList.get(i).draw(mCanvas, mBlackPaint);
        }
        invalidate();
    }

    void addInstanceInLst(int a, int b, int c) {
        int temp = 0;
        for (int i=0; i<mArrayList.size(); i++) {
            if (mArrayList.get(i).equals(new HexBlock(a, b, c))) {
                temp += 1;
            }
        }
        if (temp == 0) {
            mArrayList.add(new HexBlock(a, b, c));
            //Log.d("Hosung.Kim", a + ", " + b + ", " + c);
        }
    }

    void isBeside(HexBlock hexBlock1, HexBlock hexBlock2) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int temp = 0;
            int num1 = -1;
            int num2 = -1;
            mCanvas.drawRect(0, 0, mScreenWidth, mScreenHeight, mWhitePaint);
            float touchX = event.getX();
            float touchY = event.getY();

            //Log.d("Hosung.Kim", "touchX = " + touchX + ", touchY = " + touchY);
            for (int i=0; i<mArrayList.size(); i++) {
                if (mArrayList.get(i).isIn(touchX, touchY)) {
                    if (num1 == -1) {
                        num1 = i;
                    } else {
                        num2 = i;
                    }
                    temp += 1;
                    //Log.d("Hosung.Kim", mArrayList.get(i).x + ", " + mArrayList.get(i).y + ", " + mArrayList.get(i).z);
                }
                //Log.d("Hosung.Kim", String.valueOf(Math.pow(touchX - mArrayList.get(i).midX, 2) + Math.pow(touchY - mArrayList.get(i).midY, 2)));
                //Log.d("Hosung.Kim", String.valueOf(Math.pow(HexBlock.blockSize, 2)));
            }

            if (temp == 0) {

            } else if (temp == 1) {
                mArrayList.get(num1).fill(mCanvas, mGrayPaint);
                mArrayList.get(num1).printInfo(mCanvas, mPontPaint);
            } else if (temp == 2) {
                if (mArrayList.get(num1).getDistance(touchX, touchY) >= mArrayList.get(num2).getDistance(touchX, touchY)) {
                    mArrayList.get(num2).fill(mCanvas, mGrayPaint);
                    mArrayList.get(num2).printInfo(mCanvas, mPontPaint);
                } else {
                    mArrayList.get(num1).fill(mCanvas, mGrayPaint);
                    mArrayList.get(num1).printInfo(mCanvas, mPontPaint);
                }
            }
            //Log.d("Hosung.Kim", String.valueOf(temp));
        }
        drawHexMap();
        invalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }
}
