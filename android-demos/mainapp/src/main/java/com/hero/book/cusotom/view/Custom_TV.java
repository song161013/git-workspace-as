package com.hero.book.cusotom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.main.app.R;

/**
 * Created by songfei on 2018/6/27
 * Description：自定义TextView
 */

@SuppressLint("AppCompatCustomView")
public class Custom_TV extends TextView {
    private Paint mPaint1;
    private Paint mPaint2;

    private Paint mPaint;
    private int mViewWidth;
    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;

    private int mTranslate = 0;

    public Custom_TV(Context context) {
        super(context);
        initPaint();
    }

    public Custom_TV(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint1 = new Paint();
        mPaint1.setColor(getResources().getColor(R.color.colorPrimaryDark));
        mPaint1.setStyle(Paint.Style.FILL_AND_STROKE);

        mPaint2 = new Paint();
        mPaint2.setColor(getResources().getColor(R.color.colorAccent));
        mPaint2.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mPaint = getPaint();
                int yo = mViewWidth;
                mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0, new int[]{Color.BLUE, Color.BLACK, Color.RED,Color.CYAN}, null, Shader.TileMode.CLAMP);
//              mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//      drawRect(canvas);
        super.onDraw(canvas);
        drawText(canvas);
//      canvas.restore();
    }


    private void drawText(Canvas canvas) {
        if (mLinearGradient != null) {
            mTranslate += mViewWidth / 5;
            if (mTranslate > mViewWidth * 2) {
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            mPaint.setShader(mLinearGradient);
            postInvalidateDelayed(150);
        }
    }

    private void drawRect(Canvas canvas) {
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint1);
        canvas.drawRect(100, 100, getMeasuredWidth() - 100, getMeasuredHeight() - 100, mPaint2);
        canvas.save();
        canvas.translate(100, 0);
    }
}
