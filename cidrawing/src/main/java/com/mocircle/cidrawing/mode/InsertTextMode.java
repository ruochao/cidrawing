package com.mocircle.cidrawing.mode;

import com.mocircle.cidrawing.element.TextElement;

public class InsertTextMode extends InsertVectorElementMode {

    public InsertTextMode() {
    }

    public void setTextElement(TextElement element) {
        setVectorElement(element);
    }

}
