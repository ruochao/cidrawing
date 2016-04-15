package com.mocircle.cidrawing.view;

import android.content.Context;

public interface DrawingView {

    void setViewProxy(DrawingViewProxy viewProxy);

    void notifyViewUpdated();

    Context getContext();

}
