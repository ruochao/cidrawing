package com.mocircle.cidrawing.element;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;

import com.mocircle.cidrawing.core.Vector2;
import com.mocircle.cidrawing.element.behavior.Recyclable;

public class PhotoElement extends BoundsElement implements Recyclable {

    private Bitmap bitmap;

    public PhotoElement() {
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        if (this.bitmap != null) {
            this.bitmap.recycle();
        }
        this.bitmap = bitmap;
        calculateBoundingBox();
    }

    @Override
    public void setupElementByVector(Vector2 vector) {
        RectF box = vector.getRect();
        float scaleX = box.width() / bitmap.getWidth();
        float scaleY = box.height() / bitmap.getHeight();
        if (lockAspectRatio) {
            float scale = Math.min(scaleX, scaleY);
            resize(scale, scale, 0, 0);
        } else {
            resize(scaleX, scaleY, 0, 0);
        }
        move(box.left, box.top);
    }

    @Override
    public void drawElement(Canvas canvas) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, dataMatrix, null);
        }
    }

    @Override
    public void resetAspectRatio(AspectRatioResetMethod method) {
        float scaleX = boundingBox.width() / bitmap.getWidth();
        float scaleY = boundingBox.height() / bitmap.getHeight();
        if (method == AspectRatioResetMethod.WIDTH_FIRST) {
            scaleY = scaleX;
        } else if (method == AspectRatioResetMethod.HEIGHT_FIRST) {
            scaleX = scaleY;
        } else if (method == AspectRatioResetMethod.SMALL_FIRST) {
            float scale = Math.min(scaleX, scaleY);
            scaleX = scale;
            scaleY = scale;
        } else if (method == AspectRatioResetMethod.LARGE_FIRST) {
            float scale = Math.max(scaleX, scaleY);
            scaleX = scale;
            scaleY = scale;
        }
        resize(scaleX, scaleY, 0, 0);
    }

    @Override
    public Object clone() {
        PhotoElement element = new PhotoElement();
        cloneTo(element);
        return element;
    }

    @Override
    public void recycleElement() {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    @Override
    protected void cloneTo(BaseElement element) {
        super.cloneTo(element);
        if (element instanceof PhotoElement) {
            PhotoElement obj = (PhotoElement) element;
            if (bitmap != null) {
                obj.bitmap = Bitmap.createBitmap(bitmap);
            }
        }
    }

    @Override
    protected void calculateBoundingBox() {
        originalBoundingBox = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        boundingPath = new Path();
        boundingPath.addRect(originalBoundingBox, Path.Direction.CW);
        updateBoundingBox();
    }

}
