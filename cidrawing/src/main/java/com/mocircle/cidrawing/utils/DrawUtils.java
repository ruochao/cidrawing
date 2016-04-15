package com.mocircle.cidrawing.utils;

import android.content.Context;
import android.view.ViewConfiguration;

import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.element.VirtualElement;

public class DrawUtils {

    public static boolean isSingleTap(Context context, float x1, float y1, float x2, float y2) {
        int touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        final int deltaX = (int) (x1 - x2);
        final int deltaY = (int) (y1 - y2);
        int distance = (deltaX * deltaX) + (deltaY * deltaY);
        return distance <= touchSlop * touchSlop;
    }

    public static void clearSelections(ElementManager elementManager) {
        for (int i = elementManager.getCurrentElements().length - 1; i >= 0; i--) {
            DrawElement element = elementManager.getCurrentElements()[i];
            element.setSelected(false);
            if (element instanceof VirtualElement) {
                elementManager.removeElementFromCurrentLayer(element);
            }
        }
    }

    public static DrawElement getCurrentSelectedElement(ElementManager elementManager) {
        for (int i = elementManager.getCurrentElements().length - 1; i >= 0; i--) {
            DrawElement element = elementManager.getCurrentElements()[i];
            if (element.isSelected()) {
                return element;
            }
        }
        return null;
    }

    public static DrawElement getFirstHitElement(ElementManager elementManager, float x, float y) {
        for (int i = elementManager.getCurrentElements().length - 1; i >= 0; i--) {
            DrawElement element = elementManager.getCurrentElements()[i];
            if (element.isSelectionEnabled()) {
                if (element.hitTestForSelection(x, y)) {
                    return element;
                }
            }
        }
        return null;
    }

}
