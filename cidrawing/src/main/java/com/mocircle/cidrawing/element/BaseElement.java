package com.mocircle.cidrawing.element;

import java.io.Serializable;

public abstract class BaseElement implements Serializable {

    protected String name;

    public BaseElement() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
