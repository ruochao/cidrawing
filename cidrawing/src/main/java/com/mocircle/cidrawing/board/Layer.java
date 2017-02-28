package com.mocircle.cidrawing.board;

import com.mocircle.android.logging.CircleLog;
import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.persistence.Persistable;
import com.mocircle.cidrawing.persistence.PersistenceException;
import com.mocircle.cidrawing.persistence.PersistenceManager;
import com.mocircle.cidrawing.utils.ListUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Drawing layer to hold the elements and adornments
 */
public class Layer implements Persistable {

    private static final String TAG = "Layer";

    private static final String KEY_BOARD_ID = "boardId";
    private static final String KEY_NAME = "name";
    private static final String KEY_VISIBLE = "visible";
    private static final String KEY_SELECTED = "selected";
    private static final String KEY_ELEMENTS = "elements";

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
            element.setOrderIndex(getElementOrder(element));
        }
    }

    public void removeElement(DrawElement element) {
        if (element != null) {
            elements.remove(element);
            refreshElementsOrder();
        }
    }

    public DrawElement[] getAdornments() {
        return adornments.toArray(new DrawElement[adornments.size()]);
    }

    public void addAdornment(DrawElement element) {
        if (element != null) {
            element.setBoardId(boardId);
            adornments.add(element);
            element.setOrderIndex(getAdornmentOrder(element));
        }
    }

    public void removeAdornment(DrawElement element) {
        if (element != null) {
            adornments.remove(element);
            refreshAdornmentsOrder();
        }
    }

    public int getElementOrder(DrawElement element) {
        return elements.indexOf(element);
    }

    public int getAdornmentOrder(DrawElement element) {
        return adornments.indexOf(element);
    }

    public void arrangeElement(DrawElement element, int offset) {
        int index = elements.indexOf(element);
        ListUtils.shiftItem(elements, index, offset);
        refreshElementsOrder();
    }

    public void arrangeElementToFront(DrawElement element) {
        int index = elements.indexOf(element);
        ListUtils.shiftItemToFront(elements, index);
        refreshElementsOrder();
    }

    public void arrangeElementToBack(DrawElement element) {
        int index = elements.indexOf(element);
        ListUtils.shiftItemToBack(elements, index);
        refreshElementsOrder();
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
        refreshElementsOrder();
    }

    public void arrangeElementsToFront(List<DrawElement> elementList, ArrangeStrategy strategy) {
        arrangeElements(elementList, elementList.size(), strategy);
    }

    public void arrangeElementsToBack(List<DrawElement> elementList, ArrangeStrategy strategy) {
        arrangeElements(elementList, -elementList.size(), strategy);
    }

    @Override
    public JSONObject generateJson() {
        JSONObject object = new JSONObject();
        try {
            object.put(KEY_BOARD_ID, boardId);
            object.put(KEY_NAME, name);
            object.put(KEY_VISIBLE, visible);
            object.put(KEY_SELECTED, selected);
            object.put(KEY_ELEMENTS, PersistenceManager.persistObjects(elements));
        } catch (JSONException e) {
            throw new PersistenceException(e);
        }
        return object;
    }

    @Override
    public Map<String, byte[]> generateResources() {
        Map<String, byte[]> resMap = new HashMap<>();
        for (DrawElement element : elements) {
            Map<String, byte[]> map = element.generateResources();
            if (map != null) {
                resMap.putAll(map);
            }
        }
        return resMap;
    }

    @Override
    public void loadFromJson(JSONObject object, Map<String, byte[]> resources) {
        if (object != null) {
            boardId = object.optString(KEY_BOARD_ID);
            name = object.optString(KEY_NAME);
            visible = object.optBoolean(KEY_VISIBLE, true);
            selected = object.optBoolean(KEY_SELECTED, false);
            try {
                elements = PersistenceManager.buildObjects(object.optJSONArray(KEY_ELEMENTS), resources);
            } catch (JSONException e) {
                throw new PersistenceException(e);
            }
        }
    }

    @Override
    public void afterLoaded() {
        for (DrawElement element : elements) {
            element.afterLoaded();
        }
    }

    private void refreshElementsOrder() {
        for (int i = 0; i < elements.size(); i++) {
            elements.get(i).setOrderIndex(i);
        }
    }

    private void refreshAdornmentsOrder() {
        for (int i = 0; i < adornments.size(); i++) {
            adornments.get(i).setOrderIndex(i);
        }
    }

}
