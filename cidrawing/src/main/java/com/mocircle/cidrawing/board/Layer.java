package com.mocircle.cidrawing.board;

import com.mocircle.android.logging.CircleLog;
import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.utils.ListUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Drawing layer to hold the elements and adornments
 */
public class Layer {

    private static final String TAG = "Layer";

    protected String boardId;
    protected String name = "";
    protected boolean visible = true;
    protected boolean selected = false;
    protected List<DrawElement> elements = new LinkedList<>();
    protected List<DrawElement> adornments = new ArrayList<>();

    public Layer() {
    }

    public Layer(String name) {
        this.name = name;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public DrawElement[] getLayerObjects() {
        List<DrawElement> total = new ArrayList<>(elements.size() + adornments.size());
        total.addAll(elements);
        total.addAll(adornments);
        return total.toArray(new DrawElement[total.size()]);
    }

    public DrawElement[] getElements() {
        return elements.toArray(new DrawElement[elements.size()]);
    }

    public void addElement(DrawElement element) {
        if (element != null) {
            element.setBoardId(boardId);
            elements.add(element);
        }
    }

    public void removeElement(DrawElement element) {
        if (element != null) {
            elements.remove(element);
        }
    }

    public DrawElement[] getAdornments() {
        return adornments.toArray(new DrawElement[adornments.size()]);
    }

    public void addAdornment(DrawElement element) {
        if (element != null) {
            element.setBoardId(boardId);
            adornments.add(element);
        }
    }

    public void removeAdornment(DrawElement element) {
        if (element != null) {
            adornments.remove(element);
        }
    }

    public int getOrderIndex(DrawElement element) {
        return elements.indexOf(element);
    }

    public void arrangeElement(DrawElement element, int offset) {
        int index = elements.indexOf(element);
        ListUtils.shiftItem(elements, index, offset);
    }

    public void arrangeElementToFront(DrawElement element) {
        int index = elements.indexOf(element);
        ListUtils.shiftItemToFront(elements, index);
    }

    public void arrangeElementToBack(DrawElement element) {
        int index = elements.indexOf(element);
        ListUtils.shiftItemToBack(elements, index);
    }

    public void arrangeElements(List<DrawElement> elementList, int offset, ArrangeStrategy strategy) {
        List<Integer> indexList = new ArrayList<>();
        for (DrawElement element : elementList) {
            int index = elements.indexOf(element);
            if (index >= 0) {
                indexList.add(index);
            }
        }
        int[] indexArray = ListUtils.toIntArray(indexList);
        if (strategy == ArrangeStrategy.AS_MUCH_AS_POSSIBLE) {
            ListUtils.shiftItemsAsMuchAsPossible(elements, indexArray, offset);
        } else if (strategy == ArrangeStrategy.WITH_FIXED_DISTANCE) {
            ListUtils.shiftItemsWithFixedDistance(elements, indexArray, offset);
        } else {
            CircleLog.w(TAG, "Unsupported arrange strategy: " + strategy);
        }
    }

    public void arrangeElementsToFront(List<DrawElement> elementList, ArrangeStrategy strategy) {
        arrangeElements(elementList, elementList.size(), strategy);
    }

    public void arrangeElementsToBack(List<DrawElement> elementList, ArrangeStrategy strategy) {
        arrangeElements(elementList, -elementList.size(), strategy);
    }

}
