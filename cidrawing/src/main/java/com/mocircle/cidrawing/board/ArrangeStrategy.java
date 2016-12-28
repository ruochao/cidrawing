package com.mocircle.cidrawing.board;

/**
 * Indicate how to handle multiple elements arrangement.
 */
public enum ArrangeStrategy {

    /**
     * If elements shift out of the list, this strategy will try to shift each element to the furthest
     * distance but still keep the same order between elements.
     * For example:
     * <pre>
     * Before arrange: [A,B,C,D,E]
     * Move: A,C -> 5 steps
     * After arrange: [B,D,E,A,C]
     * </pre>
     */
    AS_MUCH_AS_POSSIBLE,

    /**
     * If elements shift out of the list, this strategy will keep the same distance and order between elements.
     * For example:
     * <pre>
     * Before arrange: [A,B,C,D,E]
     * Move: A,C -> 5 steps
     * After arrange: [B,D,A,E,C]
     * </pre>
     */
    WITH_FIXED_DISTANCE

}
