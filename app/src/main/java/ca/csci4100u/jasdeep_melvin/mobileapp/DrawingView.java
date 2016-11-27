package ca.csci4100u.jasdeep_melvin.mobileapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by jasde on 2016-11-23.
 */
public class DrawingView extends View {
    static Paint mPaint;
    static DrawingView dv;
    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mBitmapPaint;
    Context context;
    private Paint circlePaint;
    private Path circlePath;
//    static ColorPicker.OnColorChangedListener mListener;


    public DrawingView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        circlePaint = new Paint();
        circlePath = new Path();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.MITER);
        circlePaint.setStrokeWidth(4f);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);
        setDrawingCacheEnabled(true);
        dv = this;

    }

    public static DrawingView getDrawView() {
        return dv;
    }

    public static Paint getPaint() {
        return mPaint;
    }

    public static void setColor(int color) {
        mPaint.setColor(color);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;      // don't forget these
        height = h;
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.drawPath(mPath, mPaint);
        canvas.drawPath(circlePath, circlePaint);
    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;

            circlePath.reset();
            circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
        }
    }

    private void touch_up() {
        mPath.lineTo(mX, mY);
        circlePath.reset();
        // commit the path to our offscreen
        mCanvas.drawPath(mPath, mPaint);
        // kill this so we don't double draw
        mPath.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }

    public void clearDrawing() {

        setDrawingCacheEnabled(false);
        // don't forget that one and the match below,
        // or you just keep getting a duplicate when you save.

        onSizeChanged(width, height, width, height);
        invalidate();

        setDrawingCacheEnabled(true);
    }

    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "TEST_" + timeStamp + ".png";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    public void saveDrawing() {
        Bitmap whatTheUserDrewBitmap = getDrawingCache();

        whatTheUserDrewBitmap =
                ThumbnailUtils.extractThumbnail(whatTheUserDrewBitmap, 512, 512);
        // NOTE that's an incredibly useful trick for cropping/resizing squares
        // while handling all memory problems etc
        // http://stackoverflow.com/a/17733530/294884

        storeImage(whatTheUserDrewBitmap);
    }



    public static class ColorPicker extends Dialog {

        private int mInitialColor;
        private static class ColorPickerView extends View {
            private Paint mPaint;
            private Paint mCenterPaint;
            private final int[] mColors;

            ColorPickerView(Context c, int color) {
                super(c);
                mColors = new int[]{
                        0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00,
                        0xFFFFFF00, 0xFFFF0000
                };
                Shader s = new SweepGradient(0, 0, mColors, null);

                mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                mPaint.setShader(s);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(32);

                mCenterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                mCenterPaint.setColor(color);
                mCenterPaint.setStrokeWidth(5);
            }

            private boolean mTrackingCenter;
            private boolean mHighlightCenter;

            @Override
            protected void onDraw(Canvas canvas) {
                float r = CENTER_X - mPaint.getStrokeWidth() * 0.5f;

                canvas.translate(CENTER_X, CENTER_X);

                canvas.drawOval(new RectF(-r, -r, r, r), mPaint);
                canvas.drawCircle(0, 0, CENTER_RADIUS, mCenterPaint);

                if (mTrackingCenter) {
                    int c = mCenterPaint.getColor();
                    mCenterPaint.setStyle(Paint.Style.STROKE);

                    if (mHighlightCenter) {
                        mCenterPaint.setAlpha(0xFF);
                    } else {
                        mCenterPaint.setAlpha(0x80);
                    }
                    canvas.drawCircle(0, 0,
                            CENTER_RADIUS + mCenterPaint.getStrokeWidth(),
                            mCenterPaint);

                    mCenterPaint.setStyle(Paint.Style.FILL);
                    mCenterPaint.setColor(c);
                }
            }

            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                setMeasuredDimension(CENTER_X * 2, CENTER_Y * 2);
            }

            private static final int CENTER_X = 100;
            private static final int CENTER_Y = 100;
            private static final int CENTER_RADIUS = 32;

            private int floatToByte(float x) {
                int n = java.lang.Math.round(x);
                return n;
            }

            private int pinToByte(int n) {
                if (n < 0) {
                    n = 0;
                } else if (n > 255) {
                    n = 255;
                }
                return n;
            }

            private int ave(int s, int d, float p) {
                return s + java.lang.Math.round(p * (d - s));
            }

            private int interpColor(int colors[], float unit) {
                if (unit <= 0) {
                    return colors[0];
                }
                if (unit >= 1) {
                    return colors[colors.length - 1];
                }

                float p = unit * (colors.length - 1);
                int i = (int) p;
                p -= i;

                // now p is just the fractional part [0...1) and i is the index
                int c0 = colors[i];
                int c1 = colors[i + 1];
                int a = ave(Color.alpha(c0), Color.alpha(c1), p);
                int r = ave(Color.red(c0), Color.red(c1), p);
                int g = ave(Color.green(c0), Color.green(c1), p);
                int b = ave(Color.blue(c0), Color.blue(c1), p);

                return Color.argb(a, r, g, b);
            }

            private int rotateColor(int color, float rad) {
                float deg = rad * 180 / 3.1415927f;
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);

                ColorMatrix cm = new ColorMatrix();
                ColorMatrix tmp = new ColorMatrix();

                cm.setRGB2YUV();
                tmp.setRotate(0, deg);
                cm.postConcat(tmp);
                tmp.setYUV2RGB();
                cm.postConcat(tmp);

                final float[] a = cm.getArray();

                int ir = floatToByte(a[0] * r + a[1] * g + a[2] * b);
                int ig = floatToByte(a[5] * r + a[6] * g + a[7] * b);
                int ib = floatToByte(a[10] * r + a[11] * g + a[12] * b);

                return Color.argb(Color.alpha(color), pinToByte(ir),
                        pinToByte(ig), pinToByte(ib));
            }

            private static final float PI = 3.1415926f;

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                float x = event.getX() - CENTER_X;
                float y = event.getY() - CENTER_Y;
                boolean inCenter = java.lang.Math.sqrt(x * x + y * y) <= CENTER_RADIUS;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mTrackingCenter = inCenter;
                        if (inCenter) {
                            mHighlightCenter = true;
                            invalidate();
                            break;
                        }
                    case MotionEvent.ACTION_MOVE:
                        if (mTrackingCenter) {
                            if (mHighlightCenter != inCenter) {
                                mHighlightCenter = inCenter;
                                invalidate();
                            }
                        } else {
                            float angle = (float) java.lang.Math.atan2(y, x);
                            // need to turn angle [-PI ... PI] into unit [0....1]
                            float unit = angle / (2 * PI);
                            if (unit < 0) {
                                unit += 1;
                            }
                            mCenterPaint.setColor(interpColor(mColors, unit));
                            invalidate();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mTrackingCenter) {
                            if (inCenter) {
                                colorChanged(mCenterPaint.getColor());
                            }
                            mTrackingCenter = false;    // so we draw w/o halo
                            invalidate();
                        }
                        break;
                }
                return true;
            }
        }

        public ColorPicker(Context context, int initialColor) {
            super(context);

            mInitialColor = initialColor;
        }

        private static void colorChanged(int color) {
            DrawingView.setColor(color);
//            dismiss();
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(new ColorPicker.ColorPickerView(getContext(), mInitialColor));
            setTitle("Pick a Color");
        }
    }
}