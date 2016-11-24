package cn.zy.ef.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import cn.zy.ef.R;

/**
 * @author zy
 * @version 1.0
 * @des
 * @created date 16-10-31
 * <p>
 * <CircularBeadImageView
 * android:layout_width="96dp"
 * android:layout_height="96dp"
 * android:layout_margin="8dp"
 * android:src="@mipmap/lz_bp_blue"
 * CBImageView:border_color="@color/ml_white"
 * CBImageView:border_width="4dp"
 * CBImageView:press_alpha="50"
 * CBImageView:press_color="#00ff00"
 * CBImageView:radius="8dp"
 * CBImageView:shape_type="rectangle" />
 */

public class CircularBeadImageView extends ImageView {

    private Paint mPressPaint;
    private int mWidth;
    private int mHeight;
    private int mPressAlpha;
    private int mPressColor;
    private int mRadius;
    private int mShapeType;
    private int mBorderWidth;
    private int mBorderColor;

    public CircularBeadImageView(Context context) {
        super(context);
        init(context, null);
    }

    public CircularBeadImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircularBeadImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        //初始化默认值
        mPressAlpha = 64;
        mPressColor = getResources().getColor(R.color.ml_gray);
        mRadius = 16;
        mShapeType = 1;
        mBorderWidth = 0;
        mBorderColor = getResources().getColor(R.color.ml_red);

        // 获取控件的属性值
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CBImageView);
            mPressColor = array.getColor(R.styleable.CBImageView_press_color, mPressColor);
            mPressAlpha = array.getInteger(R.styleable.CBImageView_press_alpha, mPressAlpha);
            mRadius = array.getDimensionPixelSize(R.styleable.CBImageView_radius, mRadius);
            mShapeType = array.getInteger(R.styleable.CBImageView_shape_type, mShapeType);
            mBorderWidth = array.getDimensionPixelOffset(R.styleable.CBImageView_border_width_cbi, mBorderWidth);
            mBorderColor = array.getColor(R.styleable.CBImageView_border_color_cbi, mBorderColor);
            array.recycle();
        }

        // 按下的画笔设置
        mPressPaint = new Paint();
        mPressPaint.setAntiAlias(true);
        mPressPaint.setStyle(Paint.Style.FILL);
        mPressPaint.setColor(mPressColor);
        mPressPaint.setAlpha(0);
        mPressPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        setClickable(true);
        setDrawingCacheEnabled(true);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        // 获取当前控件的 drawable
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        // 这里 get 回来的宽度和高度是当前控件相对应的宽度和高度（在 xml 设置）
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        // 获取 bitmap，即传入 imageview 的 bitmap
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        drawDrawable(canvas, bitmap);
        drawPress(canvas);
        drawBorder(canvas);

    }

    private void drawDrawable(Canvas canvas, Bitmap bitmap) {
        // 画笔
        Paint paint = new Paint();
        // 颜色设置
        paint.setColor(0xffffffff);
        // 抗锯齿
        paint.setAntiAlias(true);
        //Paint 的 Xfermode，PorterDuff.Mode.SRC_IN 取两层图像的交集部门, 只显示上层图像。
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        // 标志
        int saveFlags = Canvas.MATRIX_SAVE_FLAG
                | Canvas.CLIP_SAVE_FLAG
                | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
                | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                | Canvas.CLIP_TO_LAYER_SAVE_FLAG;
        canvas.saveLayer(0, 0, mWidth, mHeight, null, saveFlags);

        if (mShapeType == 0) {
            // 画遮罩，画出来就是一个和空间大小相匹配的圆
            canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, paint);
        } else {
            // 当ShapeType = 1 时 图片为圆角矩形
            RectF rectf = new RectF(0, 0, getWidth(), getHeight());
            canvas.drawRoundRect(rectf, mRadius, mRadius, paint);
        }

        paint.setXfermode(xfermode);

        // 空间的大小 / bitmap 的大小 = bitmap 缩放的倍数
        float scaleWidth = ((float) getWidth()) / bitmap.getWidth();
        float scaleHeight = ((float) getHeight()) / bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        //bitmap 缩放
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        //draw 上去
        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.restore();
    }

    private void drawPress(Canvas canvas) {

        if (mShapeType == 0) {
            canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mPressPaint);
        } else if (mShapeType == 1) {
            RectF rectF = new RectF(0, 0, mWidth, mHeight);
            canvas.drawRoundRect(rectF, mRadius, mRadius, mPressPaint);
        }
    }

    private void drawBorder(Canvas canvas) {
        if (mBorderWidth > 0) {
            Paint paint = new Paint();
            paint.setStrokeWidth(mBorderWidth);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(mBorderColor);
            paint.setAntiAlias(true);
            if (mShapeType == 0) {
                canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, paint);
            } else {
                // 当ShapeType = 1 时 图片为圆角矩形
                RectF rectf = new RectF(0, 0, getWidth(), getHeight());
                canvas.drawRoundRect(rectf, mRadius, mRadius, paint);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPressPaint.setAlpha(mPressAlpha);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mPressPaint.setAlpha(0);
                invalidate();
                break;
            default:
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

}


