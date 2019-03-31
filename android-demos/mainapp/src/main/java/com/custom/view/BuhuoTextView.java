package com.custom.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.main.app.R;
import com.main.tools.UnityChange;

/**
 * Created by songfei on 2018/9/18
 * Description：共享柜 货道背景
 */
public class BuhuoTextView extends AppCompatTextView {

    public BuhuoTextView(Context context) {
        super(context);
//        init();
    }

    public BuhuoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init();
    }

    public BuhuoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init();
    }

    private int num;
    private int corlor;
    private int mWidth;
    private int mHeight;
    private int mMeasureWidth;
    private int mMeasureHeigth;

    private boolean isDraw = false;

    private String mSymbolTxt;
    private Paint mPaintSymbol;
    private Paint mPaintSymbolTxt;
    private int mSymbolColor = 0;

    private Path mPathSmybol;

    private Bitmap mBitmap;
    private Paint mPaintNum;

    private Paint mRoundRectPaint;
    private RectF mRoundRect;

    private Paint mBitmapPaint;

    private void init() {
        mPaintNum = new Paint();
        mPaintNum.setColor(getResources().getColor(R.color.black_color));
        mPaintNum.setTextSize(UnityChange.px2dip(getContext(), 80));

        mRoundRectPaint = new Paint();
        mRoundRectPaint.setColor(getResources().getColor(R.color.permissionBgColorGreenLight));
        mRoundRect = new RectF(0f, 0f, mWidth, mHeight);

        mBitmapPaint = new Paint();

        mPaintSymbolTxt = new Paint();
        mPaintSymbolTxt.setTextSize(UnityChange.px2dip(getContext(), 50));
        mPaintSymbolTxt.setColor(Color.WHITE);

        mPaintSymbol = new Paint();

        mPathSmybol = new Path();
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setBG(int corlor) {
        this.corlor = corlor;
    }

    public void setDrawable(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public void setDraw(boolean isDraw) {
        this.isDraw = isDraw;
    }

    public void setSymbolTxt(String txt) {
        this.mSymbolTxt = txt;
    }

    public void setSymbolColor(int color) {
        this.mSymbolColor = color;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        init();
    }

    private int measureWidth(int widthMeasureSpec) {
        int dValue = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            dValue = specSize;
        } else {
            dValue = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                dValue = Math.min(dValue, specSize);
            }
        }
        return dValue;
    }

    private int measureHeight(int heightMeasureSpec) {
        int dValue = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            dValue = specSize;
        } else {
            dValue = 160;
            if (specMode == MeasureSpec.AT_MOST) {
                dValue = Math.min(dValue, specSize);
            }
        }
        return dValue;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isDraw) {
            drawBGBitmap(canvas);
        }
        drawBG(canvas);
        drawNum(canvas);
        drawSymbolTxt(canvas);
    }

    private void drawBG(Canvas canvas) {
        int corlor = this.corlor == 0 ? R.color.gray : this.corlor;
        mRoundRectPaint.setColor(getResources().getColor(corlor));
        mRoundRectPaint.setAlpha(100);
        canvas.drawRoundRect(mRoundRect, 5, 5, mRoundRectPaint);
    }

    private void drawBGBitmap(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 30, 30, mBitmapPaint);
    }

    private void drawNum(Canvas canvas) {
        canvas.drawText(String.valueOf(num), 20, 40, mPaintNum);
    }

    private void drawSymbolTxt(Canvas canvas) {
        mSymbolColor = mSymbolColor == 0 ? R.color.black_color : mSymbolColor;
        mPaintSymbol.setColor(getResources().getColor(mSymbolColor));
        mPathSmybol.moveTo(mWidth - mWidth/3, 0);
        mPathSmybol.lineTo(mWidth - mWidth/3-80, 0);
        mPathSmybol.lineTo(150, mHeight - mHeight/3);
        mPathSmybol.setLastPoint(150, mHeight - mHeight/3-80);
      mPathSmybol.close();
        canvas.drawPath(mPathSmybol, mPaintSymbol);

        Path p1 = new Path();
        p1.moveTo(mWidth - 120, 0);
        p1.lineTo(150, mHeight - 90);
        mSymbolTxt = mSymbolTxt == null ? "空" : mSymbolTxt;
        canvas.drawTextOnPath(mSymbolTxt, p1, 35, -5, mPaintSymbolTxt);
    }

}
