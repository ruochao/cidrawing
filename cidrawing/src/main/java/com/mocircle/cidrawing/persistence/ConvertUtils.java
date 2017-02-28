package com.mocircle.cidrawing.persistence;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;

import com.mocircle.cidrawing.core.Vector2;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ConvertUtils {

    public static JSONArray matrixToJson(Matrix matrix) throws JSONException {
        if (matrix == null) {
            return null;
        }
        float[] values = new float[9];
        matrix.getValues(values);
        JSONArray array = new JSONArray();
        for (float value : values) {
            array.put(value);
        }
        return array;
    }

    public static Matrix matrixFromJson(JSONArray array) throws JSONException {
        if (array == null) {
            return null;
        }
        float[] values = new float[9];
        values[0] = (float) array.getDouble(0);
        values[1] = (float) array.getDouble(1);
        values[2] = (float) array.getDouble(2);
        values[3] = (float) array.getDouble(3);
        values[4] = (float) array.getDouble(4);
        values[5] = (float) array.getDouble(5);
        values[6] = (float) array.getDouble(6);
        values[7] = (float) array.getDouble(7);
        values[8] = (float) array.getDouble(8);
        Matrix matrix = new Matrix();
        matrix.setValues(values);
        return matrix;
    }

    public static JSONArray rectToJson(RectF rect) throws JSONException {
        if (rect == null) {
            return null;
        }
        JSONArray array = new JSONArray();
        array.put(rect.left);
        array.put(rect.top);
        array.put(rect.right);
        array.put(rect.bottom);
        return array;
    }

    public static RectF rectFromJson(JSONArray array) throws JSONException {
        if (array == null) {
            return null;
        }
        RectF rect = new RectF();
        rect.left = (float) array.getDouble(0);
        rect.top = (float) array.getDouble(1);
        rect.right = (float) array.getDouble(2);
        rect.bottom = (float) array.getDouble(3);
        return rect;
    }

    public static JSONArray pointToJson(PointF point) throws JSONException {
        if (point == null) {
            return null;
        }
        JSONArray array = new JSONArray();
        array.put(point.x);
        array.put(point.y);
        return array;
    }

    public static PointF pointFromJson(JSONArray array) throws JSONException {
        if (array == null) {
            return null;
        }
        PointF point = new PointF();
        point.x = (float) array.getDouble(0);
        point.y = (float) array.getDouble(1);
        return point;
    }

    public static JSONArray pointsToJson(Collection<PointF> points) throws JSONException {
        if (points == null) {
            return null;
        }
        JSONArray array = new JSONArray();
        for (PointF point : points) {
            array.put(point.x);
            array.put(point.y);
        }
        return array;
    }

    public static List<PointF> pointsFromJson(JSONArray array) throws JSONException {
        if (array == null) {
            return new ArrayList<>();
        }
        List<PointF> points = new ArrayList<>();
        for (int i = 0; i < array.length(); i += 2) {
            PointF point = new PointF();
            point.x = (float) array.getDouble(i);
            point.y = (float) array.getDouble(i + 1);
            points.add(point);
        }
        return points;
    }

    public static JSONArray vectorToJson(Vector2 vector) throws JSONException {
        if (vector == null) {
            return null;
        }
        JSONArray array = new JSONArray();
        array.put(vector.getPoint1().x);
        array.put(vector.getPoint1().y);
        array.put(vector.getPoint2().x);
        array.put(vector.getPoint2().y);
        return array;
    }

    public static Vector2 vectorFromJson(JSONArray array) throws JSONException {
        if (array == null) {
            return null;
        }
        PointF point1 = new PointF();
        point1.x = (float) array.getDouble(0);
        point1.y = (float) array.getDouble(1);
        PointF point2 = new PointF();
        point2.x = (float) array.getDouble(2);
        point2.y = (float) array.getDouble(3);
        Vector2 vector = new Vector2();
        vector.setPoint1(point1);
        vector.setPoint2(point2);
        return vector;
    }


}
