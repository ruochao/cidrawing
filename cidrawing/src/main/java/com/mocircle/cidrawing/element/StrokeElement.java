package com.mocircle.cidrawing.element;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

public class StrokeElement extends DrawElement {

    private transient Path strokePath = new Path();
    private List<PointF> points = new ArrayList<>();
    private boolean closeStroke;

    public StrokeElement() {
    }

    public boolean isCloseStroke() {
        return closeStroke;
    }

    public void setCloseStroke(boolean closeStroke) {
        this.closeStroke = closeStroke;
    }

    public Path getStrokePath() {
        return strokePath;
    }

    @Override
    public Path getTouchableArea() {
        if (strokePath != null) {
            return strokePath;
        } else {
            return new Path();
        }
    }

    @Override
    public Object clone() {
        StrokeElement element = new StrokeElement();
        cloneTo(element);
        return element;
    }

    @Override
    public void drawElement(Canvas canvas) {
        if (strokePath != null) {
            canvas.drawPath(strokePath, paint);
        }
    }

    public void addPoint(float x, float y) {
        points.add(new PointF(x, y));

        // Sync to path
        if (points.size() == 1) {
            strokePath.moveTo(x, y);
        } else {
            strokePath.lineTo(x, y);
        }
    }

    public void doneEditing() {
        strokePath = createStrokePath();
        updateBoundingBox();
    }

    @Override
    public void applyMatrixForData(Matrix matrix) {
        super.applyMatrixForData(matrix);

        applyMatrixForPoints(matrix);
        strokePath = createStrokePath();
    }

    @Override
    public void updateBoundingBox() {
        if (strokePath != null) {
            RectF box = new RectF();
            strokePath.computeBounds(box, true);
            setBoundingBox(box);
        }
    }

    @Override
    public RectF getOuterBoundingBox() {
        if (strokePath != null) {
            Path path = new Path(strokePath);
            path.transform(getDisplayMatrix());
            RectF box = new RectF();
            path.computeBounds(box, true);
            return box;
        }
        return new RectF();
    }

    @Override
    protected void cloneTo(BaseElement element) {
        super.cloneTo(element);
        if (element instanceof StrokeElement) {
            StrokeElement obj = (StrokeElement) element;
            if (strokePath != null) {
                obj.strokePath = new Path(strokePath);
            }
            if (points != null) {
                obj.points = new ArrayList<>();
                for (PointF p : points) {
                    obj.points.add(new PointF(p.x, p.y));
                }
            }
            obj.closeStroke = closeStroke;
        }
    }

    private Path createStrokePath() {
        Path path = new Path();
        for (int i = 0; i < points.size(); i++) {
            PointF p = points.get(i);
            if (i == 0) {
                path.moveTo(p.x, p.y);
            } else {
                path.lineTo(p.x, p.y);
            }
        }
        if (closeStroke) {
            path.close();
        }
        return path;
    }

    private void applyMatrixForPoints(Matrix matrix) {
        float[] newPoints = new float[points.size() * 2];
        int i = 0;
        for (PointF p : points) {
            newPoints[i] = p.x;
            newPoints[i + 1] = p.y;
            i += 2;
        }
        matrix.mapPoints(newPoints);
        for (int j = 0; j < newPoints.length; j += 2) {
            points.get(j / 2).set(newPoints[j], newPoints[j + 1]);
        }
    }
}
