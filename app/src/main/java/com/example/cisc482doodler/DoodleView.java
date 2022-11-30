package com.example.cisc482doodler;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class DoodleView extends View {

    private Paint brush = new Paint();
    private Path path = new Path();

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        brush.setAntiAlias(true);
        brush.setColor(Color.RED);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(8f);
    }

    public DoodleView(Context context) {
        this(context, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            path.moveTo(pointX, pointY);
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            path.lineTo(pointX, pointY);
        }
        postInvalidate();
        return false;
    }

    public void clearButtonPressed() {
        path = new Path();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, brush);
    }
}
