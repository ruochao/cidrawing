package com.mocircle.cidrawing.utils;

import com.mocircle.cidrawing.element.DrawElement;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ElementUtils {

    public static void sortElementsInLayer(List<DrawElement> elements) {
        Collections.sort(elements, new Comparator<DrawElement>() {
            @Override
            public int compare(DrawElement o1, DrawElement o2) {
                return o1.getOrderIndex() - o2.getOrderIndex();
            }
        });
    }

}
