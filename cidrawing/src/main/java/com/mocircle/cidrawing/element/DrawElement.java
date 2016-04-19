package com.mocircle.cidrawing.element;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;

import com.mocircle.cidrawing.ConfigManager;
import com.mocircle.cidrawing.DrawingBoard;
import com.mocircle.cidrawing.DrawingBoardManager;
import com.mocircle.cidrawing.PaintBuilder;
import com.mocircle.cidrawing.PaintingBehavior;
import com.mocircle.cidrawing.element.behavior.Movable;
import com.mocircle.cidrawing.element.behavior.Resizable;
import com.mocircle.cidrawing.element.behavior.Rotatable;
import com.mocircle.cidrawing.element.behavior.Selectable;
import com.mocircle.cidrawing.element.behavior.Skewable;
import com.mocircle.cidrawing.exception.DrawingBoardNotFoundException;
import com.mocircle.cidrawing.mode.ResizingDirection;

public abstract class DrawElement extends BaseElement implements Selectable, Movable, Rotatable, Skewable, Resizable {

    protected String boardId;
    protected ConfigManager configManager;
    protected PaintBuilder paintBuilder;
    protected PaintingBehavior paintingBehavior;
    protected Paint paint = new Paint();
    protected Paint debugPaintForLine;
    protected Paint debugPaintForArea;

    protected Matrix displayMatrix = new Matrix();
    protected Matrix dataMatrix = new Matrix();
    protected RectF boundingBox;
    protected PointF referencePoint;

    protected boolean selected;
    protected boolean selectionEnabled = true;
    protected SelectionStyle selectionStyle = SelectionStyle.FULL;
    protected boolean movementEnabled = true;
    protected boolean rotationEnabled = true;
    protected boolean skewEnabled = true;
    protected boolean resizingEnabled = true;

    public DrawElement() {
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
        DrawingBoard board = DrawingBoardManager.getInstance().findDrawingBoard(boardId);
        if (board == null) {
            throw new DrawingBoardNotFoundException();
        }
        configManager = board.getConfigManager();
        paintBuilder = board.getPaintBuilder();
        paintingBehavior = board.getPaintingBehavior();
        debugPaintForLine = paintBuilder.createDebugPaintForLine();
        debugPaintForArea = paintBuilder.createDebugPaintForArea();
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void drawToCanvas(Canvas canvas) {
        // Apply display matrix
        canvas.save();
        canvas.concat(getDisplayMatrix());

        // Draw element
        drawElement(canvas);

        // Draw selection
        if (isSelectionEnabled() && isSelected()) {
            drawSelection(canvas);
        }

        // Draw reference point
        if (isSelected() && hasReferencePoint()) {
            drawReferencePoint(canvas);
        }

        // Draw rotation handle
        if (isRotationEnabled() && isSelected() && getSelectionStyle() == SelectionStyle.FULL) {
            drawRotationHandle(canvas);
        }

        // Draw resizing handle
        if (isResizingEnabled() && isSelected() && getSelectionStyle() == SelectionStyle.FULL) {
            drawResizingHandle(canvas);
        }

        canvas.restore();

        // Draw debug info
        if (configManager.isDebugMode()) {
            canvas.drawRect(getOuterBoundingBox(), debugPaintForLine);
        }
    }

    public Matrix getDisplayMatrix() {
        return displayMatrix;
    }

    public Matrix getInvertedDisplayMatrix() {
        Matrix m = new Matrix();
        getDisplayMatrix().invert(m);
        return m;
    }

    public Matrix getDataMatrix() {
        return dataMatrix;
    }

    public void applyMatrixForData(Matrix matrix) {
        dataMatrix.postConcat(matrix);

        // Apply for reference point
        float[] points = new float[]{getReferencePoint().x, getReferencePoint().y};
        matrix.mapPoints(points);
        setReferencePoint(new PointF(points[0], points[1]));
    }

    /**
     * Transfer display matrix to data matrix, and reset the display matrix.
     */
    public void applyDisplayMatrixToData() {
        applyMatrixForData(getDisplayMatrix());
        getDisplayMatrix().reset();
        updateBoundingBox();
    }

    public RectF getBoundingBox() {
        return boundingBox;
    }

    /**
     * Return the actual bounding box of the graphics without transformation
     *
     * @return
     */
    public RectF getOuterBoundingBox() {
        if (boundingBox != null) {
            Path path = new Path();
            path.addRect(boundingBox, Path.Direction.CW);
            path.transform(getDisplayMatrix());
            RectF box = new RectF();
            path.computeBounds(box, true);
            return box;
        }
        return new RectF();
    }

    public void setBoundingBox(RectF boundingBox) {
        this.boundingBox = new RectF(boundingBox);
    }

    public abstract void updateBoundingBox();

    public boolean hasReferencePoint() {
        return isRotationEnabled() || isSkewEnabled();
    }

    public PointF getReferencePoint() {
        if (referencePoint == null) {
            referencePoint = new PointF(getBoundingBox().centerX(), getBoundingBox().centerY());
        }
        return referencePoint;
    }

    public PointF getActualReferencePoint() {
        float[] points = new float[2];
        getDisplayMatrix().mapPoints(points, new float[]{getReferencePoint().x, getReferencePoint().y});
        return new PointF(points[0], points[1]);
    }

    public void setReferencePoint(PointF referencePoint) {
        this.referencePoint = referencePoint;
    }

    public void drawReferencePoint(Canvas canvas) {
        paintingBehavior.drawReferencePoint(canvas, getReferencePoint());
    }

    public boolean hitTestForReferencePoint(float x, float y) {
        if (!hasReferencePoint()) {
            return false;
        }
        RectF box = paintingBehavior.getPointBox(getReferencePoint());
        float[] points = new float[2];
        getInvertedDisplayMatrix().mapPoints(points, new float[]{x, y});
        return box.contains(points[0], points[1]);
    }

    public abstract void drawElement(Canvas canvas);

    @Override
    public boolean isSelectionEnabled() {
        return selectionEnabled;
    }

    @Override
    public void setSelectionEnabled(boolean selectionEnabled) {
        this.selectionEnabled = selectionEnabled;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public SelectionStyle getSelectionStyle() {
        return selectionStyle;
    }

    @Override
    public void setSelectionStyle(SelectionStyle selectionStyle) {
        this.selectionStyle = selectionStyle;
    }

    @Override
    public void drawSelection(Canvas canvas) {
        RectF box = getBoundingBox();
        paintingBehavior.drawSelection(canvas, box);
    }

    @Override
    public boolean hitTestForSelection(float x, float y) {
        if (!isSelectionEnabled()) {
            return false;
        }
        if (getBoundingBox() != null) {
            float[] points = new float[2];
            getInvertedDisplayMatrix().mapPoints(points, new float[]{x, y});
            return getBoundingBox().contains(points[0], points[1]);
        }
        return false;
    }

    @Override
    public boolean hitTestForSelection(float x1, float y1, float x2, float y2) {
        if (!isSelectionEnabled()) {
            return false;
        }
        RectF selectionBox = new RectF(x1, y1, x2, y2);
        selectionBox.sort();
        return selectionBox.contains(getOuterBoundingBox());
    }

    @Override
    public boolean isMovementEnabled() {
        return movementEnabled;
    }

    @Override
    public void setMovementEnabled(boolean movementEnabled) {
        this.movementEnabled = movementEnabled;
    }

    @Override
    public void move(float x, float y) {
        Matrix m = new Matrix();
        m.postTranslate(x, y);
        displayMatrix.postConcat(m);
    }

    @Override
    public void moveTo(float x, float y) {
        Matrix m = new Matrix();
        m.setTranslate(x, y);
        displayMatrix.postConcat(m);
    }

    @Override
    public float getOffsetX() {
        float[] v = new float[9];
        displayMatrix.getValues(v);
        return v[Matrix.MTRANS_X];
    }

    @Override
    public float getOffsetY() {
        float[] v = new float[9];
        displayMatrix.getValues(v);
        return v[Matrix.MTRANS_Y];
    }

    @Override
    public boolean isRotationEnabled() {
        return rotationEnabled;
    }

    @Override
    public void setRotationEnabled(boolean rotationEnabled) {
        this.rotationEnabled = rotationEnabled;
    }

    @Override
    public void rotate(float degree, float px, float py) {
        Matrix m = new Matrix();
        m.postRotate(degree, px, py);
        getDisplayMatrix().postConcat(m);
    }

    @Override
    public void rotateTo(float degree, float px, float py) {
        Matrix m = new Matrix();
        m.setRotate(degree, px, py);
        getDisplayMatrix().postConcat(m);
    }

    @Override
    public float getAngle() {
        float[] v = new float[9];
        displayMatrix.getValues(v);
        float arcAngle = Math.round(Math.atan2(v[Matrix.MSKEW_X], v[Matrix.MSCALE_X]));
        return (float) Math.toDegrees(arcAngle);
    }

    @Override
    public void drawRotationHandle(Canvas canvas) {
        paintingBehavior.drawRotationHandle(canvas, getBoundingBox());
    }

    @Override
    public boolean hitTestForRotationHandle(float x, float y) {
        if (!isRotationEnabled()) {
            return false;
        }
        float[] points = new float[2];
        getInvertedDisplayMatrix().mapPoints(points, new float[]{x, y});
        RectF box = paintingBehavior.getRotationHandleBox(boundingBox);
        return box.contains(points[0], points[1]);
    }

    @Override
    public boolean isSkewEnabled() {
        return skewEnabled;
    }

    @Override
    public void setSkewEnabled(boolean skewEnabled) {
        this.skewEnabled = skewEnabled;
    }

    @Override
    public void skew(float kx, float ky, float px, float py) {
        Matrix m = new Matrix();
        m.postSkew(kx, ky, px, py);
        applyMatrixForData(m);
        updateBoundingBox();
    }

    @Override
    public void skewTo(float kx, float ky, float px, float py) {
        Matrix m = new Matrix();
        m.setSkew(kx, ky, px, py);
        applyMatrixForData(m);
        updateBoundingBox();
    }

    @Override
    public float getSkewXValue() {
        float[] v = new float[9];
        dataMatrix.getValues(v);
        return v[Matrix.MSKEW_X];
    }

    @Override
    public float getSkewYValue() {
        float[] v = new float[9];
        dataMatrix.getValues(v);
        return v[Matrix.MSKEW_X];
    }

    @Override
    public boolean isResizingEnabled() {
        return resizingEnabled;
    }

    @Override
    public void setResizingEnabled(boolean enabled) {
        this.resizingEnabled = enabled;
    }

    @Override
    public void resize(float sx, float sy, float px, float py) {
        Matrix m = new Matrix();
        m.postScale(sx, sy, px, py);
        applyMatrixForData(m);
        updateBoundingBox();
    }

    @Override
    public void resizeTo(float sx, float sy, float px, float py) {
        Matrix m = new Matrix();
        m.setScale(sx, sy, px, py);
        applyMatrixForData(m);
        updateBoundingBox();
    }

    @Override
    public void drawResizingHandle(Canvas canvas) {
        paintingBehavior.drawResizingHandle(canvas, getBoundingBox());
    }

    @Override
    public ResizingDirection hitTestForResizingHandle(float x, float y) {
        if (!isResizingEnabled()) {
            return ResizingDirection.NONE;
        }

        float[] points = new float[2];
        getInvertedDisplayMatrix().mapPoints(points, new float[]{x, y});

        RectF box = paintingBehavior.getNResizingHandleBox(boundingBox);
        if (box.contains(points[0], points[1])) {
            return ResizingDirection.NORTH;
        }

        box = paintingBehavior.getSResizingHandleBox(boundingBox);
        if (box.contains(points[0], points[1])) {
            return ResizingDirection.SOUTH;
        }

        box = paintingBehavior.getWResizingHandleBox(boundingBox);
        if (box.contains(points[0], points[1])) {
            return ResizingDirection.WEST;
        }

        box = paintingBehavior.getEResizingHandleBox(boundingBox);
        if (box.contains(points[0], points[1])) {
            return ResizingDirection.EAST;
        }

        box = paintingBehavior.getNWResizingHandleBox(boundingBox);
        if (box.contains(points[0], points[1])) {
            return ResizingDirection.NORTH_WEST;
        }

        box = paintingBehavior.getNEResizingHandleBox(boundingBox);
        if (box.contains(points[0], points[1])) {
            return ResizingDirection.NORTH_EAST;
        }

        box = paintingBehavior.getSWResizingHandleBox(boundingBox);
        if (box.contains(points[0], points[1])) {
            return ResizingDirection.SOUTH_WEST;
        }

        box = paintingBehavior.getSEResizingHandleBox(boundingBox);
        if (box.contains(points[0], points[1])) {
            return ResizingDirection.SOUTH_EAST;
        }

        return ResizingDirection.NONE;
    }

    @Override
    protected void cloneTo(BaseElement element) {
        super.cloneTo(element);
        if (element instanceof DrawElement) {
            DrawElement obj = (DrawElement) element;
            obj.boardId = boardId;
            obj.configManager = configManager;
            obj.paintBuilder = paintBuilder;
            obj.paintingBehavior = paintingBehavior;
            obj.paint = new Paint(paint);
            obj.debugPaintForLine = debugPaintForLine;
            obj.debugPaintForArea = debugPaintForArea;

            obj.displayMatrix = new Matrix(displayMatrix);
            obj.dataMatrix = new Matrix(dataMatrix);
            obj.boundingBox = new RectF(boundingBox);
            if (referencePoint != null) {
                obj.referencePoint = new PointF(referencePoint.x, referencePoint.y);
            }

            obj.selected = selected;
            obj.selectionEnabled = selectionEnabled;
            obj.selectionStyle = selectionStyle;
            obj.movementEnabled = movementEnabled;
            obj.rotationEnabled = rotationEnabled;
            obj.skewEnabled = skewEnabled;
            obj.resizingEnabled = resizingEnabled;
        }
    }
}



