package com.example.stickbalance;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class LineView extends View {
    private int height, width = 0;

    private int lineTruncation;
    private int radius = 0;
    private Paint paint;
    private boolean isInit;

    public void setLocation(double location) {
        this.location = location;
    }

    private double location;

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initLine() {
        height = getHeight();
        width = getWidth();

        int min = Math.min(height, width);
        radius = min / 2;

        lineTruncation = min / 5;

        paint = new Paint();
        isInit = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInit) {
            initLine();
        }

        canvas.drawColor(getResources().getColor(R.color.background));
        drawCenter(canvas);
        drawLine(canvas, location);

        postInvalidate();
        invalidate();
    }

    private void drawLine(Canvas canvas, double angle) {
        int lineLength = radius - lineTruncation;
        double angleRadians = (Math.PI / 180.0) * angle;
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        paint.setStrokeWidth(5);
        canvas.drawLine(
                width / 2,
                height / 2,
                (float) (width / 2 + (Math.cos(angleRadians) * lineLength)),
                (float) (height / 2 + (Math.sin(angleRadians) * lineLength)),
                paint
        );
    }

    private void drawCenter(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2, height / 2, 12, paint);
    }
}
