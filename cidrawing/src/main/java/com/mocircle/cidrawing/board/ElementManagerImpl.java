package com.mocircle.cidrawing.board;

import com.mocircle.cidrawing.element.DrawElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElementManagerImpl implements ElementManager {

    private String boardId;
    private List<Layer> layers = new ArrayList<>();
    private Layer currentLayer;
    private int index = 1;

    private List<LayerChangeListener> layerChangeListeners = new ArrayList<>();

    public ElementManagerImpl(String boardId) {
        this.boardId = boardId;
    }

    @Override
    public Layer[] getLayers() {
        return layers.toArray(new Layer[layers.size()]);
    }

    @Override
    public Layer getCurrentLayer() {
        return currentLayer;
    }

    @Override
    public void selectLayer(Layer selectedLayer) {
        currentLayer = selectedLayer;
        for (Layer layer : layers) {
            layer.setSelected(layer == selectedLayer);
        }
    }

    @Override
    public Layer selectFirstVisibleLayer() {
        for (Layer layer : layers) {
            if (layer.isVisible()) {
                selectLayer(layer);
                return currentLayer;
            }
        }
        return null;
    }

    @Override
    public Layer createNewLayer() {
        return createNewLayer("Layer " + index++);
    }

    @Override
    public Layer createNewLayer(String name) {
        Layer layer = new Layer(name);
        addLayer(layer);
        if (currentLayer == null) {
            currentLayer = layer;
        }
        return layer;
    }

    @Override
    public void addLayer(Layer layer) {
        layer.setBoardId(boardId);
        layers.add(layer);

        // Notify layer added
        for (LayerChangeListener listener : layerChangeListeners) {
            listener.onLayerChanged();
        }
    }

    @Override
    public void removeLayer(Layer layer) {
        layers.remove(layer);
        if (currentLayer == layer) {
            currentLayer = null;
        }

        // Notify layer removed
        for (LayerChangeListener listener : layerChangeListeners) {
            listener.onLayerChanged();
        }
    }

    @Override
    public void removeAllLayers() {
        layers.clear();
        currentLayer = null;

        // Notify layer removed
        for (LayerChangeListener listener : layerChangeListeners) {
            listener.onLayerChanged();
        }
    }

    @Override
    public void showLayer(Layer layer) {
        layer.setVisible(true);
    }

    @Override
    public void showAllLayers() {
        for (Layer layer : layers) {
            layer.setVisible(true);
        }
    }

    @Override
    public void hideLayer(Layer layer) {
        layer.setVisible(false);
    }

    @Override
    public void hideAllLayers() {
        for (Layer layer : layers) {
            layer.setVisible(false);
        }
    }

    @Override
    public void addLayerChangeListener(LayerChangeListener listener) {
        layerChangeListeners.add(listener);
    }

    @Override
    public void removeLayerChangeListener(LayerChangeListener listener) {
        layerChangeListeners.remove(listener);
    }

    @Override
    public DrawElement[] getVisibleElements() {
        List<DrawElement> elements = new ArrayList<>();
        for (Layer layer : layers) {
            if (layer.isVisible()) {
                elements.addAll(Arrays.asList(layer.getElements()));
            }
        }
        return elements.toArray(new DrawElement[elements.size()]);
    }

    @Override
    public DrawElement[] getCurrentElements() {
        if (getCurrentLayer() != null) {
            return getCurrentLayer().getElements();
        } else {
            return new DrawElement[0];
        }
    }

    @Override
    public void addElementToCurrentLayer(DrawElement element) {
        getCurrentLayer().addElement(element);
    }

    @Override
    public void removeElementFromCurrentLayer(DrawElement element) {
        getCurrentLayer().removeElement(element);
    }
}
