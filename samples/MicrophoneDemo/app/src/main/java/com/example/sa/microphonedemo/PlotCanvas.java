package com.example.sa.microphonedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class PlotCanvas extends View {

    /**
     * Used to pad the right side in order to draw the values at the axis
     */
    private static final int RIGHT_PADDING = 100;


    private double[] histogramPoints = new double[]{0};

    /**
     * Some basic colors as 0xaarrggbb to use for graphs, if the number of series are larger that the length of this
     * array
     * modulus operator will be used to avoid indexOutOfBoundsException.
     */
    private static final int[] colors = new int[]{
            0xff000000,
            0xffff0000,
            0xff00ff00,
            0xff0000ff,
            0xffffff00,
            0xff00ffff
    };

    /**
     * Flag indicating whether the view has been initialized.
     */
    private boolean isInitialized;

    /**
     * Begin stored values used when plotting
     */
    private int halfY;
    private float maxY;
    private float yScale = 1;
    private int height;
    private int quarterY;
    private String quarterYText = "0.5";
    private String negativeQuarterYText = "-0.5";
    private int rightMinusPadding;
    private String maxYText = "";
    private String minYText = "";
    private Paint paint;
    private boolean line = true;
    private boolean antiAlias = true;
    private boolean isHistogram;
    private Paint histogramPaint;

    /**
     * End stored values used when plotting
     */

    public PlotCanvas(Context context) {
        this(context, null);
    }

    public PlotCanvas(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlotCanvas(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setUpAxisPaint();
    }

    /**
     * Sets up the paint used to draw the axis
     */
    private void setUpAxisPaint() {
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setStrokeWidth(4);
        this.paint.setFakeBoldText(true);
        this.paint.setTextSize(20);
        this.paint.setTextAlign(Paint.Align.LEFT);
        this.paint.setColor(0xff000000);
    }

    final Object writeLock = new Object();
    public void putPoints(Object[] inputVector) {

        if (!isInitialized) {
            this.histogramPaint = new Paint();
            this.histogramPaint.setColor(0xff000000);
            this.histogramPaint.setStrokeWidth(10);
            this.paint.setTextAlign(Paint.Align.RIGHT);
            isInitialized = true;
        }
        synchronized (writeLock) {
            boolean changed = false;
            // Copy input to histogramPoints and check if there is a new max value.
            if (histogramPoints == null || histogramPoints.length != inputVector.length) {
                histogramPoints = new double[inputVector.length];
            }
            for (int i = 0; i < inputVector.length; i++) {
                histogramPoints[i] = (double)inputVector[i];
                double val = Math.abs(histogramPoints[i]);
                if (val > maxY) {
                    maxY = (float) val;
                    changed = true;
                }
            }
            // Update axis if needed.
            if (changed) {
                this.yScale = ((getMeasuredHeight() - 40) / 2) / maxY;

                this.quarterYText = String.format("%.2f", maxY / 2);
                this.negativeQuarterYText = String.format("-%.2f", maxY / 2);

                this.maxYText = String.format("%.2f", maxY);
                this.minYText = String.format("-%.2f", maxY);
            }
        }
        postInvalidate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.height = getMeasuredHeight();
        this.halfY = getMeasuredHeight() / 2;
        this.quarterY = getMeasuredHeight() / 4;
        this.rightMinusPadding = getMeasuredWidth() - RIGHT_PADDING;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInitialized) {
            if (histogramPoints != null) {
                synchronized (writeLock) {
                    int left = 80;
                    int xStep = Math.min((getMeasuredWidth()) / histogramPoints.length, 10);
                    for (int i = 0; i < histogramPoints.length; i++) {
                        float y = (float) histogramPoints[i] * yScale;
                        if (y >= 0) {
                            canvas.drawRect(left + i * xStep, getHeight() / 2 - y, left + (i + 1) * xStep, getHeight() / 2, histogramPaint);
                        } else {
                            canvas.drawRect(left + i * xStep, getHeight() / 2, left + (i + 1) * xStep, getHeight() / 2 - y, histogramPaint);
                        }
                    }
                }
            }
            drawAxis(canvas, paint);
        }
    }

    /**
     * draws the axis for the graph.
     *
     * @param canvas canvas for the view
     * @param paint  paint used to draw the canvas
     */
    private void drawAxis(Canvas canvas, Paint paint) {
            int left = 80;
            canvas.drawLine(left, 0, left, height, paint);

            canvas.drawLine(left, halfY, left - 15, halfY, paint);

            // top marker
            canvas.drawLine(left, halfY - maxY * yScale, left - 15, halfY - maxY * yScale,
                    paint);

            // upper quarter marker
            canvas.drawLine(left, quarterY + 5, left - 15, quarterY + 5, paint);

            // lower quarter marker
            canvas.drawLine(left, 3 * quarterY - 5, left - 15, 3 * quarterY - 5, paint);

            // bottom marker
            canvas.drawLine(left, halfY + maxY * yScale, left - 15, halfY + maxY * yScale,
                    paint);

            canvas.drawText("0", left - 20, halfY + 5, paint);

            canvas.drawText(quarterYText, left - 20, quarterY + 10, paint);
            canvas.drawText(negativeQuarterYText, left - 20, 3 * quarterY, paint);

            canvas.drawText(maxYText, left - 20, halfY - maxY * yScale + 5, paint);
            canvas.drawText(minYText, left - 20, halfY + maxY * yScale + 5, paint);
    }

    public void reset() {
        isInitialized = false;
        maxY = 0.1f;
        yScale = ((getMeasuredHeight() - 40) / 2) / maxY;
        histogramPoints = null;
    }
}
