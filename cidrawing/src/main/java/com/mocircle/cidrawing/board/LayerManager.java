package com.mocircle.cidrawing.board;

public interface LayerManager {

    interface LayerChangeListener {
        void onLayerChanged();
    }

    Layer[] getLayers();

    Layer getCurrentLayer();

    void selectLayer(Layer selectedLayer);

    Layer selectFirstVisibleLayer();

    Layer createNewLayer();

    Layer createNewLayer(String name);

    void addLayer(Layer layer);

    void removeLayer(Layer layer);

    void removeAllLayers();

    void showLayer(Layer layer);

    void showAllLayers();

    void hideLayer(Layer layer);

    void hideAllLayers();

    void addLayerChangeListener(LayerChangeListener listener);

    void removeLayerChangeListener(LayerChangeListener listener);

}
