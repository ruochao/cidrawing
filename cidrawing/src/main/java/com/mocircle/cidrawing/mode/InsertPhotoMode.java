package com.mocircle.cidrawing.mode;

import com.mocircle.cidrawing.element.PhotoElement;

public class InsertPhotoMode extends InsertVectorElementMode {

    public InsertPhotoMode() {
    }

    public void setPhotoElement(PhotoElement element) {
        setVectorElement(element);
    }

}
