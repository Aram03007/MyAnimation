package com.example.narek.myanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Narek on 4/7/16.
 */
public class AnimPathView extends View implements View.OnClickListener {

    private Path path;
    private Paint paint;

    private static final long animSpeedInMs = 2;
    private static final long animMsBetweenStrokes = 1000;
    private long animLastUpdate;
    private boolean animRunning;
    private int animCurrentCountour;
    private float animCurrentPos;
    private Path animPath;
    private PathMeasure animPathMeasure;

    public AnimPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDisplayKanjiView();
    }

    public AnimPathView(Context context) {
        super(context);
        initDisplayKanjiView();
    }

    private final void initDisplayKanjiView() {

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(0xff336699);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(30);

        path = new Path();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {




            path.addArc(400, 300, 1200, 1100, 0, 360);
            path.lineTo(1200,2000);
            path.lineTo(400,2000);
            path.lineTo(400,700);
        }

        animRunning = false;

        this.setOnClickListener(this);
    }

    public void setPath(Path p) {
        path = p;
    }

    @Override
    public void onClick(View v) {
        Log.d("dimensions", "width, heght " + getWidth() + " " + getHeight());
        startAnimation();
    }

    public void startAnimation() {
        animRunning = true;
        animPathMeasure = null;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (animRunning) {
            drawAnimation(canvas);
        } else {
            drawStatic(canvas);
        }
    }

    private void drawAnimation(Canvas canvas) {
        if (animPathMeasure == null) {
            // Start of animation. Set it up.
            animPathMeasure = new PathMeasure(path, false);
            animPathMeasure.nextContour();
            animPath = new Path();


            animLastUpdate = System.currentTimeMillis();
            animCurrentCountour = 0;
            animCurrentPos = 0.0f;
        } else {
            // Get time since last frame
            long now = System.currentTimeMillis();
            long timeSinceLast = now - animLastUpdate;

            if (animCurrentPos == 0.0f) {
                timeSinceLast -= animMsBetweenStrokes;
            }

            if (timeSinceLast > 0) {
                // Get next segment of path
                float newPos = (float)(timeSinceLast) / animSpeedInMs + animCurrentPos;
                boolean moveTo = (animCurrentPos == 0.0f);
                animPathMeasure.getSegment(animCurrentPos, newPos, animPath, moveTo);
                animCurrentPos = newPos;
                animLastUpdate = now;

                // If this stroke is done, move on to next
                if (newPos > animPathMeasure.getLength()) {
                    animCurrentPos = 0.0f;
                    animCurrentCountour++;
                    boolean more = animPathMeasure.nextContour();
                    // Check if finished
                    if (!more) { animRunning = false; }
                }
            }

            // Draw path
            canvas.drawPath(animPath, paint);
        }

        invalidate();
    }

    private void drawStatic(Canvas canvas) {
        canvas.drawPath(path, paint);
    }
}
