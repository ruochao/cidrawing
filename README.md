# CiDrawing

[![Build Status](https://travis-ci.org/mocircle/cidrawing.svg?branch=master)](https://travis-ci.org/mocircle/cidrawing)

CiDrawing is a vector graphics library for Android, it provides a custom view and together with a set of tools to manage vector graphics drawing.

## Supported Features
### Elements
 * Stroke element (Pen)
 * Group element (Group/Ungroup)
 * Shape element
  * Line
  * Rectangle

### Transformations
 * Move
 * Rotate
 * Resize
 * Skew
 * ReShape

### Functions
 * Custom paint (color, width, style, etc)
 * Multiple layer support
 * Unlimited undo/redo
 * Element group/ungroup
 * Multiple elements selection and transformation

## How to Use
Include view in your layout as:
```
<com.mocircle.cidrawing.view.CiDrawingView
    android:id="@+id/drawing_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```
Create drawing board object and set up the view:
```
CiDrawingView drawingView = (CiDrawingView) findViewById(R.id.drawing_view);
DrawingBoard drawingBoard = DrawingBoardManager.getInstance().createNewBoard();
drawingBoard.setupDrawingView(drawingView);
```

## Sample Project
Please check out the sample project at [CiDrawing Sample] (https://github.com/mocircle/cidrawing/tree/master/cidrawingsample)

## Add to your project
Current CiDrawing is still under developing, not yet published. So please compile source code yourself.

## License

CiDrawing is released under version 2.0 of the Apache License.
