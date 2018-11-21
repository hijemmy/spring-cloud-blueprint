package com.jemmy.services.barcode.model.enums;

public enum TextAlignEnum {
    LEFT("left"),
    CENTER("center"),
    RIGHT("right"),
    JUSTIFY("justify");

    private String align;

    TextAlignEnum(String align) {
        this.align = align;
    }

    public String getAlign() {
        return align;
    }
}
