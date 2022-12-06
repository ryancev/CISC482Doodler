package com.example.cisc482doodler;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
public class DoodleView extends View {

    private static class UserAction {
        public Path path;
        public Paint brush;

        public UserAction(Path path, Paint brush) {
            this.path = path;
            this.brush = brush;
        }
    }

    private Paint brush = new Paint();
    private Path path = new Path();


//    private ArrayList<Path> paths = new ArrayList<>();
//    private ArrayList<Paint> brushes = new ArrayList<>();

    private ArrayList<UserAction> actions = new ArrayList<>();
    private ArrayList<UserAction> undoneActions = new ArrayList<>();


    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        brush.setAntiAlias(true);
        brush.setColor(Color.RED);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(8f);

//        paths.add(path);
//        brushes.add(brush);
    }

    public DoodleView(Context context) {
        this(context, null);
    }

    public void changeColor(int color) {
        float currWidth = brush.getStrokeWidth();
        brush = new Paint();
        brush.setAntiAlias(true);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(currWidth);
        brush.setColor(color);
//        brushes.add(brush);
        path = new Path();
//        paths.add(path);
        invalidate();
    }

    public void changeWidth(int width) {
        int currColor = brush.getColor();
        brush = new Paint();
        brush.setAntiAlias(true);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setColor(currColor);
        int newWidth = 8 + width;
        brush.setStrokeWidth(newWidth);
//        brushes.add(brush);
        path = new Path();
//        paths.add(path);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            path.moveTo(pointX, pointY);
            actions.add(new UserAction(path, brush));
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (undoneActions.size() > 0) {
                undoneActions.clear();
            }
            path.lineTo(pointX, pointY);
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            actions.remove(actions.size() - 1);
            actions.add(new UserAction(path, brush));
            path = new Path();
        }
        postInvalidate();
        return false;
    }

    public void clearButtonPressed() {
        path = new Path();
//        paths.clear();
//        brushes.clear();
        actions.clear();
        undoneActions.clear();
//        paths.add(path);
//        brushes.add(brush);
        invalidate();
    }

    public void undoButtonPressed() {
        if (actions.size() > 0) {
            UserAction undoneAction = actions.get(actions.size() - 1);
            undoneActions.add(undoneAction);
            actions.remove(actions.size() - 1);
            path = new Path();
            invalidate();
        }
    }

    public void redoButtonPressed() {
        if (undoneActions.size() > 0) {
            actions.add(undoneActions.get(undoneActions.size() - 1));
            undoneActions.remove(undoneActions.size() - 1);
            invalidate();
        }
    }

    public int getCurrColor() {
        return brush.getColor();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (UserAction action : actions) {
            canvas.drawPath(action.path, action.brush);
        }
//        for (int i = 0; i < paths.size(); i++) {
//            path = paths.get(i);
//            brush = brushes.get(i);
//            canvas.drawPath(path, brush);
//        }
    }
}
