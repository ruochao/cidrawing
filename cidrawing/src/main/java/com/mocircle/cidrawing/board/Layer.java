package com.mocircle.cidrawing.board;

import com.mocircle.cidrawing.element.DrawElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Drawing layer to hold the elements.
 */
public class Layer {

    protected String boardId;
    protected String name = "";
    protected boolean visible = true;
    protected boolean selected = false;
    protected List<DrawElement> elements = new ArrayList<>();

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

    public DrawElement[] getElements() {
        return elements.toArray(new DrawElement[elements.size()]);
    }

    public void setElements(List<DrawElement> elements) {
        this.elements = elements;
    }

    public void addElement(DrawElement element) {
        if (element != null) {
            elements.add(element);
            element.setBoardId(boardId);
        }
    }

    public void removeElement(DrawElement element) {
        if (element != null) {
            elements.remove(element);
        }
    }

}
