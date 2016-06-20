# CiDrawing

[![Build Status](https://travis-ci.org/mocircle/cidrawing.svg?branch=master)](https://travis-ci.org/mocircle/cidrawing)

CiDrawing is a vector graphics library for Android, it provides a custom view and together with a set of tools to manage vector graphics drawing.

## Supported Features
### Elements
 * Stroke element (Pen)
 * Group element (Group/Ungroup)
 * Shape element
  * Line
  * Arc
  * Rectangle
  * Square
  * Oval
  * Circle
  * Triangle (Isosceles triangle, Right triangle)
 * Photo element
 * Text element

### Functions
 * Basic element transformation
  * Move
  * Rotate
  * Resize
  * Skew
  * Re-Shape (Reset transformation without change drawing)
 * Element selection (Single/Multiple)
  * Rectangle select
  * Oval select
  * Lasso select
 * Custom paint (Color, Width, Style, etc)
 * Multiple layer support
 * Unlimited undo/redo
 * Element group/ungroup
 * Eraser (Object eraser)
 * Path operation
  * Union
  * Intersect
  * Different/Reserved Differernt
  * Xor
 * Element alignment
  * Horizontal (Left, Center, Right)
  * Vertical (Top, Middle, Bottom)
 * Element flip (based on reference point)
  * Horizontal
  * Vertical

## How to Use
Include view in your layout as:
```xml
<com.mocircle.cidrawing.view.CiDrawingView
    android:id="@+id/drawing_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```
Create drawing board object and set up the view:
```java
CiDrawingView drawingView = (CiDrawingView) findViewById(R.id.drawing_view);
DrawingBoard drawingBoard = DrawingBoardManager.getInstance().createNewBoard();
drawingBoard.setupDrawingView(drawingView);
```

## Sample Project
Please check out the sample project at [CiDrawing Sample] (https://github.com/mocircle/cidrawing/tree/master/cidrawingsample)

### Screenshots
![Screenshot 1](https://github.com/mocircle/cidrawing/blob/master/docs/sample1.png) 
![Screenshot 2](https://github.com/mocircle/cidrawing/blob/master/docs/sample2.png)
![Screenshot 3](https://github.com/mocircle/cidrawing/blob/master/docs/sample3.png)

## Add to your project
Current CiDrawing is still under developing, not yet published. So please compile source code yourself.

## License

CiDrawing is released under version 2.0 of the Apache License.
