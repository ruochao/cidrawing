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

    public void setBitmap(Bitmap bitmap) {
        if (this.bitmap != null) {
            this.bitmap.recycle();
        }
        this.bitmap = bitmap;
        initBoundingBox();
    }

    @Override
    public void setupElementByVector(Vector2 vector) {
        RectF box = vector.getRect();
        float scaleX = box.width() / bitmap.getWidth();
        float scaleY = box.height() / bitmap.getHeight();
        resize(scaleX, scaleY, 0, 0);
        move(box.left, box.top);

    }

    @Override
    public void drawElement(Canvas canvas) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, dataMatrix, null);
        }
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
            obj.bitmap = Bitmap.createBitmap(bitmap);
        }
    }

    @Override
    protected void initBoundingBox() {
        originalBoundingBox = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        boundingPath = new Path();
        boundingPath.addRect(originalBoundingBox, Path.Direction.CW);
        updateBoundingBox();
    }


}
