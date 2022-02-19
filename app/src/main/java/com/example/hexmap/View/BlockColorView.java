package com.example.hexmap.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class BlockColorView extends View {
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private int    mScreenWidth;
    private int    mScreenHeight;
    private Paint  mBlockPaint;

    public BlockColorView(Context context) {
        super(context);
    }

    public BlockColorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);

        mScreenWidth   = metrics.widthPixels/2;
        mScreenHeight  = metrics.heightPixels;
        mBitmap = Bitmap.createBitmap(mScreenWidth, mScreenWidth, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        mBlockPaint = new Paint();
        mBlockPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    public void drawColor(int r, int g, int b) {
        mBlockPaint.setColor(Color.rgb(r, g, b));
        mCanvas.drawRect(0, 0, mScreenWidth, mScreenWidth, mBlockPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }
}
