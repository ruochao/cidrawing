package com.mocircle.cidrawing.element;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

import com.mocircle.cidrawing.core.Vector2;

public class TextElement extends BoundsElement {

    protected String text = "";
    protected float textSize;

    public TextElement() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        initBoundingBox();
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        setupPaint();
    }

    @Override
    public void setPaint(Paint paint) {
        super.setPaint(paint);
        setupPaint();
        initBoundingBox();
    }

    @Override
    public void setupElementByVector(Vector2 vector) {
        RectF box = vector.getRect();
        move(box.left, box.top);
    }

    @Override
    public void drawElement(Canvas canvas) {
        canvas.drawText(text, 0, 0, paint);
    }

    @Override
    public Object clone() {
        TextElement element = new TextElement();
        cloneTo(element);
        return element;
    }

    @Override
    public boolean isResizingEnabled() {
        return false;
    }

    @Override
    public boolean isSkewEnabled() {
        return false;
    }

    @Override
    protected void initBoundingBox() {
        Rect box = new Rect();
        paint.getTextBounds(text, 0, text.length(), box);
        originalBoundingBox = new RectF(box);
        boundingPath = new Path();
        boundingPath.addRect(originalBoundingBox, Path.Direction.CW);
        updateBoundingBox();
    }

    @Override
    protected void cloneTo(BaseElement element) {
        super.cloneTo(element);
        if (element instanceof TextElement) {
            TextElement obj = (TextElement) element;
            obj.text = text;
            obj.textSize = textSize;
        }
    }

    private void setupPaint() {
        paint.setTextSize(textSize);
    }

}
