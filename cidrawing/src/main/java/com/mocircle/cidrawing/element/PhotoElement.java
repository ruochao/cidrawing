package com.mocircle.cidrawing.element;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;

import com.mocircle.android.logging.CircleLog;
import com.mocircle.cidrawing.core.Vector2;
import com.mocircle.cidrawing.element.behavior.Recyclable;
import com.mocircle.cidrawing.element.behavior.SupportVector;
import com.mocircle.cidrawing.persistence.PersistenceException;
import com.mocircle.cidrawing.persistence.PersistenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class PhotoElement extends BoundsElement implements SupportVector, Recyclable {

    private static final String TAG = "PhotoElement";
    private static final String KEY_BITMAP = "bitmap";
    private static final String KEY_WIDTH = "width";
    private static final String KEY_HEIGHT = "height";

    private Bitmap bitmap;
    private transient String bitmapKey;

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
    public JSONObject generateJson() {
        JSONObject object = super.generateJson();
        try {
            if (bitmap != null) {
                object.put(KEY_BITMAP, getBitmapKey());
                object.put(KEY_WIDTH, bitmap.getWidth());
                object.put(KEY_HEIGHT, bitmap.getHeight());
            }
        } catch (JSONException e) {
            throw new PersistenceException(e);
        }
        return object;
    }

    @Override
    public Map<String, byte[]> generateResources() {
        Map<String, byte[]> resMap = new HashMap<>();
        if (bitmap != null) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
            bitmap.copyPixelsToBuffer(byteBuffer);
            byte[] bytes = byteBuffer.array();
            resMap.put(getBitmapKey(), bytes);
        }
        return resMap;
    }

    @Override
    public void loadFromJson(JSONObject object, Map<String, byte[]> resources) {
        super.loadFromJson(object, resources);
        if (object != null) {
            String bitmapKey = object.optString(KEY_BITMAP, null);
            if (bitmapKey != null) {
                byte[] bytes = resources.get(bitmapKey);
                if (bytes != null) {
                    if (bitmap != null && !bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                    bitmap = Bitmap.createBitmap(object.optInt(KEY_WIDTH), object.optInt(KEY_HEIGHT), Bitmap.Config.ARGB_8888);
                    bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(bytes));
                } else {
                    CircleLog.w(TAG, "Cannot find resource for key=" + bitmapKey);
                }
            }
        }
    }

    @Override
    public void afterLoaded() {
        calculateBoundingBox();
        updateBoundingBoxWithDataMatrix();
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
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
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

    private String getBitmapKey() {
        if (bitmapKey == null) {
            bitmapKey = PersistenceManager.generateResourceName();
        }
        return bitmapKey;
    }

}
