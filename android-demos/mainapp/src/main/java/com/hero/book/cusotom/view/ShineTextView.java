package com.hero.book.cusotom.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.main.app.R;

public class ShineTextView extends AppCompatTextView {

    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private Paint mPaint;
    private int mViewWidth = 0;
    private int mTranslate = 0;
    private Context mContext;
    private Bitmap b1, b2;
    private Canvas mCanvas;

    public ShineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        b1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.add).copy(Bitmap.Config.ARGB_8888, true);
//        b1 = Bitmap.createBitmap(50, 20, Bitmap.Config.ARGB_8888);
//        b2 = Bitmap.createBitmap(50, 20, Bitmap.Config.ARGB_8888);
        b2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.link).copy(Bitmap.Config.ARGB_8888, true);

        mCanvas = new Canvas(b2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(
                        0, 0, mViewWidth, 0, new int[]{Color.BLUE,
                        0xffffffff, Color.BLUE}, null, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(b1, 10, 10, null);
//        canvas.drawBitmap(b2, 20, 20, null);
        canvas.drawColor(Color.RED);
        mCanvas.drawBitmap(b2, 50, 50, null);
        mCanvas.drawColor(Color.BLUE);

        postInvalidateDelayed(10);

        if (true) return;
        if (mGradientMatrix != null) {
            mTranslate += mViewWidth / 5;
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }
    }
}
