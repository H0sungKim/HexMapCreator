package com.example.hexmap.View;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * to be fixed
 *
 * font
 */

class HexBlock {
    public double x;
    public double y;
    public double midX;
    public double midY;
    private Path path = new Path();

    public String name;
    public int r;
    public int g;
    public int b;
    public Paint backGroundPaint = new Paint();

    public static int blockSize;
    public static int textSize;

    public static int mapX;
    public static int mapY;

    HexBlock(double x, double y) {
        this.x = x;
        this.y = y;
        this.name = "";
        this.r = 225;
        this.g = 225;
        this.b = 225;
        this.backGroundPaint.setColor(Color.rgb(225, 225, 225));
        this.backGroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.backGroundPaint.setTextSize(textSize);
        this.backGroundPaint.setAntiAlias(true);

        this.midX = mapX + 3*blockSize*(y-x)/2;
        this.midY = mapY - Math.sqrt(3)*blockSize*(x/2+y/2);

        this.path.moveTo((float)(this.midX-blockSize), (float)(this.midY));
        this.path.lineTo((float)(this.midX-blockSize/2), (float)(this.midY+Math.sqrt(3)*blockSize/2));
        this.path.lineTo((float)(this.midX+blockSize/2), (float)(this.midY+Math.sqrt(3)*blockSize/2));
        this.path.lineTo((float)(this.midX+blockSize), (float)(this.midY));
        this.path.lineTo((float)(this.midX+blockSize/2), (float)(this.midY-Math.sqrt(3)*blockSize/2));
        this.path.lineTo((float)(this.midX-blockSize/2), (float)(this.midY-Math.sqrt(3)*blockSize/2));
        this.path.close();
    }

    HexBlock(double x, double y, String name, int r, int g, int b) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.r = r;
        this.g = g;
        this.b = b;
        this.backGroundPaint.setColor(Color.rgb(r, g, b));
        this.backGroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.backGroundPaint.setTextSize(textSize);
        this.backGroundPaint.setAntiAlias(true);

        this.midX = mapX + 3*blockSize*(y-x)/2;
        this.midY = mapY - Math.sqrt(3)*blockSize*(x/2+y/2);

        this.path.moveTo((float)(this.midX-blockSize), (float)(this.midY));
        this.path.lineTo((float)(this.midX-blockSize/2), (float)(this.midY+Math.sqrt(3)*blockSize/2));
        this.path.lineTo((float)(this.midX+blockSize/2), (float)(this.midY+Math.sqrt(3)*blockSize/2));
        this.path.lineTo((float)(this.midX+blockSize), (float)(this.midY));
        this.path.lineTo((float)(this.midX+blockSize/2), (float)(this.midY-Math.sqrt(3)*blockSize/2));
        this.path.lineTo((float)(this.midX-blockSize/2), (float)(this.midY-Math.sqrt(3)*blockSize/2));
        this.path.close();
    }

    void setPath() {
        this.midX = mapX + 3*blockSize*(y-x)/2;
        this.midY = mapY - Math.sqrt(3)*blockSize*(x/2+y/2);

        path = new Path();
        this.path.moveTo((float)(this.midX-blockSize), (float)(this.midY));
        this.path.lineTo((float)(this.midX-blockSize/2), (float)(this.midY+Math.sqrt(3)*blockSize/2));
        this.path.lineTo((float)(this.midX+blockSize/2), (float)(this.midY+Math.sqrt(3)*blockSize/2));
        this.path.lineTo((float)(this.midX+blockSize), (float)(this.midY));
        this.path.lineTo((float)(this.midX+blockSize/2), (float)(this.midY-Math.sqrt(3)*blockSize/2));
        this.path.lineTo((float)(this.midX-blockSize/2), (float)(this.midY-Math.sqrt(3)*blockSize/2));
        this.path.close();
    }

    void draw(Canvas canvas, Paint paint) {
        canvas.drawPath(this.path, this.backGroundPaint);
        canvas.drawPath(this.path, paint);
        //Log.d("Hosung.Kim", "midX = " + midX + ", midY = " + midY);
    }

    void setBackGroundColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.backGroundPaint.setColor(Color.rgb(r, g, b));
    }

    void setTypeface(Typeface typeface) {
        this.backGroundPaint.setTypeface(typeface);
    }

    void drawOutline(Canvas canvas, Paint paint) {
        canvas.drawPath(this.path, paint);
    }

    void setName(String name) {
        this.name = name;
    }

    boolean isIn(float touchX, float touchY) {
        return Math.pow(touchX - midX, 2) + Math.pow(touchY - midY, 2) <= Math.pow(blockSize, 2);
    }

    // to be fixed
    void fill(int hasLine, Canvas canvas, Paint paintLine, Paint paintBackG) {
        //canvas.drawCircle((float)this.midX, (float)this.midY, 20, paint);
        canvas.drawPath(this.path, paintBackG);
        if (hasLine == 0) {
            canvas.drawPath(this.path, paintLine);
        }
    }

    void drawName(Canvas canvas, Paint blackPaint, Paint whitePaint) {
        canvas.drawText(this.name, (float) this.midX, (float) this.midY-blackPaint.ascent()/2, whitePaint);
        canvas.drawText(this.name, (float) this.midX, (float) this.midY-blackPaint.ascent()/2, blackPaint);
    }

    void drawInfo(Canvas canvas, int screenHeight, Paint outlinePaint) {
        //canvas.drawRect(0, 0, ScreenWidth, ScreenHeight/30, eraser);
        canvas.drawText(this.name, screenHeight/50, screenHeight/20, this.backGroundPaint);
        canvas.drawText("(" + (int)this.x + ", " + (int)this.y + ")", screenHeight/50, 2*screenHeight/20, this.backGroundPaint);
        canvas.drawText(this.name, screenHeight/50, screenHeight/20, outlinePaint);
        canvas.drawText("(" + (int)this.x + ", " + (int)this.y + ")", screenHeight/50, 2*screenHeight/20, outlinePaint);
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
            if (this.x == ((HexBlock) obj).x && this.y == ((HexBlock) obj).y) {
                return true;
            } else {
                return false;
            }
        }
    }
}

public class HexMapView extends View {

    private ArrayList<HexBlock> mMapList = new ArrayList<HexBlock>();
    //private ArrayList<HexBlock> mCreateList = new ArrayList<HexBlock>();


    private int mScreenWidth;
    private int mScreenHeight;

    private int mMapSize;

    private Paint    mBlackPaint;
    private Paint    mWhitePaint;
    private Paint    mGrayPaintL;
    private Paint    mGrayPaintT;
    private Paint    mGrayLinePaint;
    private Paint    mWhiteInfoPaint;
    private Paint    mBlackPontPaint;
    private Paint    mWhitePontPaint;
    private Bitmap   mBitmap;
    private Canvas   mCanvas;

    private Point    mOldPoint;

    //private int mCreateBlockMode=0;
    private int mNowClickedBlockNum;
    private HexBlock mNowClickedBlock;

    int zoomStatus  = 0;
    int oldDistance = 0;
    int newDistance = 0;

    private BlockRefactor mBlockRefactor;

    private AssetManager am;
    private Typeface plain;
    private Typeface bold;

    //private Random   mRandom;

    public HexMapView(Context context) {
        super(context);
    }

    public HexMapView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);

        am  = this.getContext().getAssets();
        plain = Typeface.createFromAsset(am, "font/gowunbatang.ttf");
        bold = Typeface.create(plain, Typeface.BOLD);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);

        mScreenWidth   = metrics.widthPixels;
        mScreenHeight  = metrics.heightPixels;

        mBlackPaint = new Paint();
        mBlackPaint.setColor(Color.rgb(0, 0, 0));
        mBlackPaint.setStyle(Paint.Style.STROKE);
        mBlackPaint.setStrokeWidth(6);

        mBlackPontPaint = new Paint();
        mBlackPontPaint.setColor(Color.rgb(0, 0, 0));
        mBlackPontPaint.setStyle(Paint.Style.STROKE);
        mBlackPontPaint.setTextSize(mScreenHeight/1000+HexBlock.blockSize/3);
        mBlackPontPaint.setAntiAlias(true);
        mBlackPontPaint.setTextAlign(Paint.Align.CENTER);
        mBlackPontPaint.setTypeface(bold);

        mWhitePontPaint = new Paint();
        mWhitePontPaint.setColor(Color.rgb(255, 255, 255));
        mWhitePontPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mWhitePontPaint.setTextSize(mScreenHeight/1000+HexBlock.blockSize/3);
        mWhitePontPaint.setAntiAlias(true);
        mWhitePontPaint.setTextAlign(Paint.Align.CENTER);
        mWhitePontPaint.setTypeface(bold);

        mWhiteInfoPaint = new Paint();
        mWhiteInfoPaint.setColor(Color.rgb(255, 255, 255));
        mWhiteInfoPaint.setStyle(Paint.Style.STROKE);
        mWhiteInfoPaint.setTextSize(mScreenHeight/20);
        mWhiteInfoPaint.setAntiAlias(true);
        mWhiteInfoPaint.setStrokeWidth(2);
        mWhiteInfoPaint.setTypeface(bold);

        mGrayPaintL = new Paint();
        mGrayPaintL.setColor(Color.rgb(127, 127, 127));
        mGrayPaintL.setAlpha(95);
        mGrayPaintL.setStyle(Paint.Style.FILL_AND_STROKE);

        mGrayLinePaint = new Paint();
        mGrayLinePaint.setColor(Color.rgb(127, 127, 127));
        mGrayLinePaint.setStyle(Paint.Style.STROKE);
        mGrayLinePaint.setStrokeWidth(3);

        mGrayPaintT = new Paint();
        mGrayPaintT.setColor(Color.rgb(127, 127, 127));
        mGrayPaintT.setStyle(Paint.Style.STROKE);
        mGrayPaintT.setStrokeWidth(2);

        mWhitePaint = new Paint();
        mWhitePaint.setColor(Color.rgb(255, 255, 255));
        mWhitePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mBitmap = Bitmap.createBitmap(metrics.widthPixels, metrics.heightPixels, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        mOldPoint = new Point(0, 0);

        //mRandom = new Random();

        setClickable(true);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getPointerCount() == 1) {
                    mCanvas.drawRect(0, 0, mScreenWidth, mScreenHeight, mWhitePaint);
                    int touchedBlock = 0;
                    int num1 = -1;
                    int num2 = -1;

                    float touchX = event.getX();
                    float touchY = event.getY();

                    //Log.d("Hosung.Kim", "touchX = " + touchX + ", touchY = " + touchY);
                    for (int i = 0; i < mMapList.size(); i++) {
                        // hexblock 범위 정의
                        if (mMapList.get(i).isIn(touchX, touchY)) {
                            if (num1 == -1) {
                                num1 = i;
                            } else {
                                num2 = i;
                            }
                            touchedBlock += 1;
                            //Log.d("Hosung.Kim", mArrayList.get(i).x + ", " + mArrayList.get(i).y + ", " + mArrayList.get(i).z);
                        }
                        //Log.d("Hosung.Kim", String.valueOf(Math.pow(touchX - mArrayList.get(i).midX, 2) + Math.pow(touchY - mArrayList.get(i).midY, 2)));
                        //Log.d("Hosung.Kim", String.valueOf(Math.pow(HexBlock.blockSize, 2)));
                    }

                    drawHexMap();
                    if (touchedBlock == 0) {
                        mNowClickedBlockNum = -1;
                        mNowClickedBlock = null;

                        /*if (mCreateBlockMode == 1) {
                            // to be fixed
                            // createBlockList에 추가 & drawHexMap
                            // Math.floor 버림
                            // Math.ceil 올림
                            Log.d("Hosung.Kim", "create mode activated");

                            double leastDistance = -1;
                            int x=0;
                            int y=0;
                            for (int i=(int)Math.ceil(touchY/Math.sqrt(3)/HexBlock.blockSize-touchX/3/HexBlock.blockSize-5/6); i<=(int)Math.floor(touchY/Math.sqrt(3)/HexBlock.blockSize-touchX/3/HexBlock.blockSize+5/6); i++) {
                                for (int j=(int)Math.ceil(touchY/Math.sqrt(3)/HexBlock.blockSize+touchX/3/HexBlock.blockSize-5/6); j<=(int)Math.floor(touchY/Math.sqrt(3)/HexBlock.blockSize+touchX/3/HexBlock.blockSize+5/6); j++) {
                                    if (leastDistance == -1 || Math.pow(touchX - HexBlock.mapX - 3*HexBlock.blockSize*(j-i)/2, 2) + Math.pow(touchY - HexBlock.mapY + Math.sqrt(3)*HexBlock.blockSize*(i/2+j/2), 2) < leastDistance) {
                                        leastDistance = Math.pow(touchX - HexBlock.mapX - 3*HexBlock.blockSize*(j-i)/2, 2) + Math.pow(touchY - HexBlock.mapY + Math.sqrt(3)*HexBlock.blockSize*(i/2+j/2), 2);
                                        x = i;
                                        y = j;
                                        Log.d("Hosung.Kim", "x = " + x + ", y = " + y);
                                    }
                                }
                            }
                            if (!mMapList.contains(new HexBlock(x, y))) {
                                mCreateList.add(new HexBlock(x, y, "Null", 200, 200, 255));
                            }

                        }*/
                    } else if (touchedBlock == 1) {
                        mNowClickedBlockNum = num1;
                        mNowClickedBlock = mMapList.get(num1);
                        mMapList.get(num1).fill(0, mCanvas, mBlackPaint, mGrayPaintL);
                        mMapList.get(num1).drawInfo(mCanvas, mScreenHeight, mWhiteInfoPaint);
                    } else if (touchedBlock == 2) {
                        if (mMapList.get(num1).getDistance(touchX, touchY) >= mMapList.get(num2).getDistance(touchX, touchY)) {
                            mNowClickedBlockNum = num2;
                            mNowClickedBlock = mMapList.get(num2);
                            mMapList.get(num2).fill(0, mCanvas, mBlackPaint, mGrayPaintL);
                            mMapList.get(num2).drawInfo(mCanvas, mScreenHeight, mWhiteInfoPaint);
                        } else {
                            mNowClickedBlockNum = num1;
                            mNowClickedBlock = mMapList.get(num1);
                            mMapList.get(num1).fill(0, mCanvas, mBlackPaint, mGrayPaintL);
                            mMapList.get(num1).drawInfo(mCanvas, mScreenHeight, mWhiteInfoPaint);
                        }
                    }
                }

                return false;
            }
        });


        //Log.d("Hosung.Kim", mScreenWidth + ", " + mScreenHeight);

    }

    public void firstSetLst(int mapSize) {
        mMapSize = mapSize;
        HexBlock.textSize = mScreenHeight/20;
        HexBlock.blockSize = mScreenWidth / 3 / (mapSize+1);
        HexBlock.mapX = mScreenWidth/2;
        HexBlock.mapY = mScreenHeight/2;
        mWhitePontPaint.setTextSize(mScreenHeight/1000+HexBlock.blockSize/3);
        mBlackPontPaint.setTextSize(mScreenHeight/1000+HexBlock.blockSize/3);
        mMapList.clear();

        for (int i=0; i<mapSize; i++) {
            for (int j=0; j<i+1; j++) {
                addInstanceInLst(i, j);
                addInstanceInLst(j, i);
                addInstanceInLst(-i, -j);
                addInstanceInLst(-j, -i);
                addInstanceInLst(i-j, -j);
                addInstanceInLst(-j, i-j);
            }
        }
        for (int i=0; i<mMapList.size(); i++) {
            mMapList.get(i).setTypeface(bold);
        }
        drawHexMap();
    }

    public void loadMap(String mapCode) {

        String[] hexBlockLst = mapCode.split("#");
        mMapSize = Integer.parseInt(hexBlockLst[1]);
        HexBlock.textSize = mScreenHeight/20;
        HexBlock.blockSize = mScreenWidth / 3 / (mMapSize+1);
        HexBlock.mapX = mScreenWidth/2;
        HexBlock.mapY = mScreenHeight/2;
        mMapList.clear();
        for (int i=2; i<hexBlockLst.length; i++) {
            String[] instanceInfo = hexBlockLst[i].split(";");
            if (instanceInfo[2].equals("n")) {
                mMapList.add(new HexBlock(Integer.parseInt(instanceInfo[0]), Integer.parseInt(instanceInfo[1]), "", Integer.parseInt(instanceInfo[3]), Integer.parseInt(instanceInfo[4]), Integer.parseInt(instanceInfo[5])));
            } else {
                mMapList.add(new HexBlock(Integer.parseInt(instanceInfo[0]), Integer.parseInt(instanceInfo[1]), instanceInfo[2], Integer.parseInt(instanceInfo[3]), Integer.parseInt(instanceInfo[4]), Integer.parseInt(instanceInfo[5])));
            }

        }
        for (int i=0; i<mMapList.size(); i++) {
            mMapList.get(i).setTypeface(bold);
        }
        drawHexMap();
    }

    void drawHexMap() {
        for (int i=0; i<mMapList.size(); i++) {
            mMapList.get(i).setPath();
            double midX = mMapList.get(i).midX;
            double midY = mMapList.get(i).midY;
            if (midX >= -1 * HexBlock.blockSize && midX < mScreenWidth + HexBlock.blockSize
            && midY >= -1 * HexBlock.blockSize && midY < mScreenHeight + HexBlock.blockSize) {

                if (HexBlock.blockSize >= mScreenWidth/20) {
                    mMapList.get(i).draw(mCanvas, mGrayLinePaint);
                    mMapList.get(i).drawName(mCanvas, mBlackPontPaint, mWhitePontPaint);
                } else {
                    mMapList.get(i).draw(mCanvas, mGrayPaintT);
                }

            }
        }
        // to be fixed
        // createBlockList도 그리기
        /*for (int i=0; i<mCreateList.size(); i++) {
            mCreateList.get(i).setPath();
            double midX = mCreateList.get(i).midX;
            double midY = mCreateList.get(i).midY;
            if (midX >= -1 * HexBlock.blockSize && midX < mScreenWidth + HexBlock.blockSize
                    && midY >= -1 * HexBlock.blockSize && midY < mScreenHeight + HexBlock.blockSize) {
                mCreateList.get(i).draw(mCanvas, mGrayLinePaint);
            }
        }*/
        invalidate();
    }

    void addInstanceInLst(int a, int b) {
        int temp = 0;
        for (int i=0; i<mMapList.size(); i++) {
            if (mMapList.get(i).equals(new HexBlock(a, b))) {
                temp += 1;
            }
        }
        if (temp == 0) {
            mMapList.add(new HexBlock(a, b));
            //Log.d("Hosung.Kim", a + ", " + b + ", " + c);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX;
        float touchY;

        float touchX2;
        float touchY2;

        final int ZOOM_STOP  = 0;
        final int ZOOM_ING   = 1;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (event.getPointerCount() == 1) {
                touchX = event.getX();
                touchY = event.getY();
                mOldPoint.x = (int)touchX;
                mOldPoint.y = (int)touchY;
            }
            //Log.d("Hosung.Kim", String.valueOf(temp));
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

            if (event.getPointerCount() == 1) {
                if (zoomStatus == ZOOM_STOP) {
                    touchX = event.getX();
                    touchY = event.getY();

                    //Log.d("Hosung.Kim", "Activated 1");
                    // to be fixed
                    //if ((HexBlock.mapX+touchX-mOldPoint.x)*15 / HexBlock.blockSize > -mScreenWidth && (HexBlock.mapX+touchX-mOldPoint.x)*15 / HexBlock.blockSize < mScreenWidth*2
                            //&& (HexBlock.mapY+touchY-mOldPoint.y)*15 / HexBlock.blockSize > -mScreenHeight && (HexBlock.mapY+touchY-mOldPoint.y)*15 / HexBlock.blockSize < mScreenHeight*2) {
                        //Log.d("Hosung.Kim", "Activated 2");
                    HexBlock.mapX += touchX - mOldPoint.x;
                    HexBlock.mapY += touchY - mOldPoint.y;
                    mCanvas.drawRect(0, 0, mScreenWidth, mScreenHeight, mWhitePaint);
                    drawHexMap();
                    if (mNowClickedBlockNum >= 0) {
                        mMapList.get(mNowClickedBlockNum).setPath();
                        mMapList.get(mNowClickedBlockNum).drawInfo(mCanvas, mScreenHeight, mWhiteInfoPaint);
                        mMapList.get(mNowClickedBlockNum).fill(1, mCanvas, mBlackPaint, mGrayPaintL);
                        mMapList.get(mNowClickedBlockNum).drawOutline(mCanvas, mBlackPaint);
                        if (HexBlock.blockSize >= mScreenWidth/20) {
                            mMapList.get(mNowClickedBlockNum).drawName(mCanvas, mBlackPontPaint, mWhitePontPaint);
                        }
                    }
                    mOldPoint.x = (int) touchX;
                    mOldPoint.y = (int) touchY;
                    invalidate();
                    //}
                }
            } else if (event.getPointerCount() == 2) {
                mNowClickedBlock = null;
                touchX = event.getX(0);
                touchY = event.getY(0);

                touchX2 = event.getX(1);
                touchY2 = event.getY(1);

                if (zoomStatus == ZOOM_STOP) {
                    oldDistance = (int) Math.sqrt(Math.pow(touchX2-touchX, 2) + Math.pow(touchY2-touchY, 2));
                    zoomStatus = ZOOM_ING;
                } else if (zoomStatus == ZOOM_ING) {
                    newDistance = (int) Math.sqrt(Math.pow(touchX2 - touchX, 2) + Math.pow(touchY2 - touchY, 2));
                    // ===
                    /*if ((newDistance - oldDistance) * HexBlock.blockSize / 900 != 0) {
                        float mapX = HexBlock.mapX - mScreenWidth / 2;
                        float mapY = HexBlock.mapY - mScreenHeight / 2;
                        float dif = newDistance - oldDistance;
                        Log.d("Hosung.Kim", "================================");
                        Log.d("Hosung.Kim", mapX + ", " + mapY);
                        Log.d("Hosung.Kim", newDistance - oldDistance + "");
                        Log.d("Hosung.Kim", dif / 900 + "");
                        mapX = mapX * (dif / 1500 + 1);
                        mapY = mapY * (dif / 1500 + 1);
                        HexBlock.mapX = (int) mapX + mScreenWidth / 2;
                        HexBlock.mapY = (int) mapY + mScreenHeight / 2;
                        Log.d("Hosung.Kim", HexBlock.mapX + ", " + HexBlock.mapY);
                        // ===
                    }*/
                    if (newDistance > oldDistance) {
                        if (HexBlock.blockSize < mScreenWidth/2) {
                            HexBlock.blockSize = HexBlock.blockSize + (newDistance - oldDistance) * HexBlock.blockSize / 900;
                        }
                    } else if (newDistance < oldDistance) {
                        if (HexBlock.blockSize > mScreenWidth/3/(mMapSize+1)) {
                            HexBlock.blockSize = HexBlock.blockSize + (newDistance - oldDistance) * HexBlock.blockSize / 900;
                        }
                    }
                    mWhitePontPaint.setTextSize(mScreenHeight/1000+HexBlock.blockSize/3);
                    mBlackPontPaint.setTextSize(mScreenHeight/1000+HexBlock.blockSize/3);
                    mCanvas.drawRect(0, 0, mScreenWidth, mScreenHeight, mWhitePaint);
                    drawHexMap();
                    oldDistance = newDistance;
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            zoomStatus = ZOOM_STOP;
        }
        //drawHexMap();
        invalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    public void applyChange(String name, int r, int g, int b) {
        mCanvas.drawRect(0, 0, mScreenWidth, mScreenHeight, mWhitePaint);
        mNowClickedBlock.setName(name);
        mNowClickedBlock.setBackGroundColor(r, g, b);
        mNowClickedBlock = null;
        drawHexMap();
    }

    public void deleteBlock() {
        mCanvas.drawRect(0, 0, mScreenWidth, mScreenHeight, mWhitePaint);
        for (int i=0; i<mMapList.size(); i++) {
            if (mMapList.get(i).equals(mNowClickedBlock)) {
                mMapList.remove(i);
            }
        }
        mNowClickedBlock = null;
        drawHexMap();
    }

    public String mapCode() {
        /*String mapCode = "HexMapCode" + "#" + mMapSize;
        for (int i=0; i<mMapList.size(); i++) {
            mapCode = mapCode + "#"+ (int)mMapList.get(i).x + ";" + (int)mMapList.get(i).y + ";";
            if (mMapList.get(i).name.equals("")) {
                mapCode = mapCode + "n";
            } else {
                mapCode = mapCode + mMapList.get(i).name;
            }
            mapCode = mapCode + ";" + mMapList.get(i).r + ";" + mMapList.get(i).g + ";" + mMapList.get(i).b;
        }
        return mapCode;*/
        StringBuffer sb = new StringBuffer();
        sb.append("HexMapCode#");
        sb.append(mMapSize);
        for (int i=0; i<mMapList.size(); i++) {
            sb.append("#");
            sb.append((int)mMapList.get(i).x);
            sb.append(";");
            sb.append((int)mMapList.get(i).y);
            sb.append(";");
            if (mMapList.get(i).name.equals("")) {
                sb.append("n");
            } else {
                sb.append(mMapList.get(i).name);
            }
            sb.append(";");
            sb.append(mMapList.get(i).r);
            sb.append(";");
            sb.append(mMapList.get(i).g);
            sb.append(";");
            sb.append(mMapList.get(i).b);
        }
        String mapCode = sb.toString();
        return mapCode;
    }

    private static final String CAPTURE_PATH = "/Download";

    public void captureMap(View view) {

        view.buildDrawingCache();
        Bitmap captureView = view.getDrawingCache();
        FileOutputStream fos;

        String strFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + CAPTURE_PATH;
        File folder = new File(strFolderPath);
        if(!folder.exists()) {
            folder.mkdirs();
        }

        String strFilePath = strFolderPath + "/" + System.currentTimeMillis() + ".png";
        File fileCacheItem = new File(strFilePath);

        try {
            fos = new FileOutputStream(fileCacheItem);
            captureView.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(getContext(), "이미지 저장 완료", Toast.LENGTH_LONG).show();
            Log.d("Hosung.Kim", "Capture Activated");
        } catch (FileNotFoundException e) {
            Log.d("Hosung.Kim", e.getMessage());
            Toast.makeText(getContext(), "이미지 저장 실패\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public void setBlockRefactor(BlockRefactor br) {
        mBlockRefactor = br;
    }

    public void refactorBlockDialog() {
        if (mNowClickedBlock != null) {
            mBlockRefactor.openRefactorDialog((int) mNowClickedBlock.x, (int) mNowClickedBlock.y, mNowClickedBlock.name, mNowClickedBlock.r, mNowClickedBlock.g, mNowClickedBlock.b);
        }
    }

    public void createBlockDialog() {
        /*if (mCreateBlockMode == 0) {
            mCreateBlockMode = 1;
            mNowClickedBlockNum = -1;
            mNowClickedBlock = null;
        } else if (mCreateBlockMode == 1) {
            // to be fixed
            // createBlockList를 mArrayList에 추가 & createBlockList clear
            mMapList.addAll(mCreateList);
            mCreateList.clear();
            mCreateBlockMode = 0;
        }*/
        mBlockRefactor.openCreateDialog();
    }

    public void createBlock(int x, int y) {
        HexBlock create = new HexBlock(x, y);

        int temp = 0;
        for (int i=0; i<mMapList.size(); i++) {
            if (mMapList.get(i).equals(create)) {
                temp += 1;
            }
        }
        if (temp == 0) {
            create.setTypeface(bold);
            mMapList.add(create);

            mNowClickedBlockNum = -1;
            mNowClickedBlock = null;

            mCanvas.drawRect(0, 0, mScreenWidth, mScreenHeight, mWhitePaint);
            drawHexMap();

            //Log.d("Hosung.Kim", a + ", " + b + ", " + c);
        }





    }
}