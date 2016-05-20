package com.mocircle.cidrawingsample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.mocircle.cidrawing.DrawingBoard;
import com.mocircle.cidrawing.DrawingBoardManager;
import com.mocircle.cidrawing.board.Layer;
import com.mocircle.cidrawing.board.LayerManager;
import com.mocircle.cidrawing.element.PhotoElement;
import com.mocircle.cidrawing.element.TextElement;
import com.mocircle.cidrawing.element.shape.ArcElement;
import com.mocircle.cidrawing.element.shape.CircleElement;
import com.mocircle.cidrawing.element.shape.IsoscelesTriangleElement;
import com.mocircle.cidrawing.element.shape.LineElement;
import com.mocircle.cidrawing.element.shape.OvalElement;
import com.mocircle.cidrawing.element.shape.RectElement;
import com.mocircle.cidrawing.element.shape.RightTriangleElement;
import com.mocircle.cidrawing.element.shape.SquareElement;
import com.mocircle.cidrawing.mode.DrawingMode;
import com.mocircle.cidrawing.mode.InsertPhotoMode;
import com.mocircle.cidrawing.mode.InsertShapeMode;
import com.mocircle.cidrawing.mode.InsertTextMode;
import com.mocircle.cidrawing.mode.PointerMode;
import com.mocircle.cidrawing.mode.eraser.ObjectEraserMode;
import com.mocircle.cidrawing.mode.stroke.StrokeMode;
import com.mocircle.cidrawing.mode.transformation.MoveMode;
import com.mocircle.cidrawing.mode.transformation.ResizeMode;
import com.mocircle.cidrawing.mode.transformation.RotateMode;
import com.mocircle.cidrawing.mode.transformation.SkewMode;
import com.mocircle.cidrawing.operation.GroupElementOperation;
import com.mocircle.cidrawing.operation.ReshapeOperation;
import com.mocircle.cidrawing.operation.UngroupElementOperation;
import com.mocircle.cidrawing.view.CiDrawingView;

import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private CiDrawingView drawingView;
    private DrawerLayout drawer;
    private RecyclerView layersView;

    private DrawingBoard drawingBoard;
    private LayerAdapter layerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingBoard = DrawingBoardManager.getInstance().createNewBoard();
        drawingBoard.getConfigManager().setDebugMode(true);
        setupView();
        setupLayer();
        setupDefault();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.switch_debug_menu:
                switchDebugMode();
                return true;
            case R.id.show_info_menu:
                showInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setScrimColor(Color.TRANSPARENT);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drawingView = (CiDrawingView) findViewById(R.id.drawing_view);
        drawingBoard.setupDrawingView(drawingView);
    }

    private void setupDefault() {
        drawingBoard.getDrawingContext().setColor(Color.BLACK);
        drawingBoard.getDrawingContext().setStrokeWidth(6);
        select(null);
        drawingBoard.getElementManager().createNewLayer();
        drawingBoard.getElementManager().selectFirstVisibleLayer();
    }

    private void setupLayer() {
        layersView = (RecyclerView) findViewById(R.id.layers_view);
        layersView.setLayoutManager(new LinearLayoutManager(this));
        layerAdapter = new LayerAdapter();
        layersView.setAdapter(layerAdapter);

        layerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                drawingBoard.getDrawingView().notifyViewUpdated();
            }
        });
        layerAdapter.setOnItemClick(new LayerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Layer layer) {
                drawingBoard.getElementManager().selectLayer(layer);
                layerAdapter.notifyDataSetChanged();
            }
        });
        drawingBoard.getElementManager().addLayerChangeListener(new LayerManager.LayerChangeListener() {
            @Override
            public void onLayerChanged() {
                layerAdapter.setLayers(Arrays.asList(drawingBoard.getElementManager().getLayers()));
            }
        });
    }

    // First row

    public void select(View v) {
        drawingBoard.getDrawingContext().setDrawingMode(new PointerMode());
    }

    public void transform(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.menu_transform, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                DrawingMode mode = null;
                switch (item.getItemId()) {
                    case R.id.move_menu:
                        mode = new MoveMode(true);
                        break;
                    case R.id.rotate_menu:
                        mode = new RotateMode(true);
                        break;
                    case R.id.resize_menu:
                        mode = new ResizeMode(true);
                        break;
                    case R.id.skew_menu:
                        mode = new SkewMode(true);
                        break;
                }
                drawingBoard.getDrawingContext().setDrawingMode(mode);
                return true;
            }
        });
        popup.show();
    }

    public void eraser(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.menu_eraser, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                DrawingMode mode = null;
                switch (item.getItemId()) {
                    case R.id.object_eraser_menu:
                        mode = new ObjectEraserMode();
                        break;
                }
                drawingBoard.getDrawingContext().setDrawingMode(mode);
                return true;
            }
        });
        popup.show();
    }

    // Second row

    public void pen(View v) {
        drawingBoard.getDrawingContext().setDrawingMode(new StrokeMode());
    }

    public void insertShape(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.menu_insert_shape, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                InsertShapeMode mode = new InsertShapeMode();
                drawingBoard.getDrawingContext().setDrawingMode(mode);
                switch (item.getItemId()) {
                    case R.id.line_menu:
                        mode.setShapeType(LineElement.class);
                        break;
                    case R.id.arc_menu:
                        mode.setShapeType(ArcElement.class);
                        break;
                    case R.id.rect_menu:
                        mode.setShapeType(RectElement.class);
                        break;
                    case R.id.squre_menu:
                        mode.setShapeType(SquareElement.class);
                        break;
                    case R.id.oval_menu:
                        mode.setShapeType(OvalElement.class);
                        break;
                    case R.id.circle_menu:
                        mode.setShapeType(CircleElement.class);
                        break;
                    case R.id.isosceles_triangle_menu:
                        mode.setShapeType(IsoscelesTriangleElement.class);
                        break;
                    case R.id.right_triangle_menu:
                        RightTriangleElement shape = new RightTriangleElement();
                        shape.setLeftRightAngle(true);
                        mode.setShapeInstance(shape);
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    public void insertPhoto(View v) {
        InsertPhotoMode mode = new InsertPhotoMode();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getAssets().open("sample.jpg"));
            PhotoElement element = new PhotoElement();
            element.setBitmap(bitmap);
            element.setLockAspectRatio(true);
            mode.setPhotoElement(element);
        } catch (IOException e) {
            e.printStackTrace();
        }
        drawingBoard.getDrawingContext().setDrawingMode(mode);
    }

    public void insertText(View v) {
        InsertTextMode mode = new InsertTextMode();
        TextElement element = new TextElement();
        element.setText("Sample text");
        element.setTextSize(60);
        mode.setTextElement(element);
        drawingBoard.getDrawingContext().setDrawingMode(mode);
    }

    // Left drawer panel

    public void addLayer(View v) {
        drawingBoard.getElementManager().createNewLayer();
        layerAdapter.notifyDataSetChanged();
    }

    public void removeLayer(View v) {
        drawingBoard.getElementManager().removeLayer(drawingBoard.getElementManager().getCurrentLayer());
        drawingBoard.getElementManager().selectFirstVisibleLayer();
        layerAdapter.notifyDataSetChanged();
    }

    // Right drawer panel

    public void reshape(View v) {
        drawingBoard.getOperationManager().executeOperation(new ReshapeOperation());
        drawingView.notifyViewUpdated();
    }

    public void group(View v) {
        drawingBoard.getOperationManager().executeOperation(new GroupElementOperation());
    }

    public void ungroup(View v) {
        drawingBoard.getOperationManager().executeOperation(new UngroupElementOperation());
    }

    // Bottom row

    public void changeColor(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.menu_color, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.black_menu:
                        drawingBoard.getDrawingContext().setColor(Color.BLACK);
                        break;
                    case R.id.blue_menu:
                        drawingBoard.getDrawingContext().setColor(Color.BLUE);
                        break;
                    case R.id.red_menu:
                        drawingBoard.getDrawingContext().setColor(Color.RED);
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    public void changeWidth(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.menu_width, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.large_menu:
                        drawingBoard.getDrawingContext().setStrokeWidth(10);
                        break;
                    case R.id.normal_menu:
                        drawingBoard.getDrawingContext().setStrokeWidth(6);
                        break;
                    case R.id.small_menu:
                        drawingBoard.getDrawingContext().setStrokeWidth(2);
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    public void undo(View v) {
        drawingBoard.getOperationManager().undo();
    }

    public void redo(View v) {
        drawingBoard.getOperationManager().redo();
    }

    // More menu

    public void switchDebugMode() {
        drawingBoard.getConfigManager().setDebugMode(!drawingBoard.getConfigManager().isDebugMode());
        drawingView.notifyViewUpdated();
    }

    public void showInfo() {
    }

}
