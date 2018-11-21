package com.jemmy.services.barcode.model.enums;

public enum TextAlignEnum {
    LEFT,
    CENTER,
    RIGHT,
    JUSTIFY;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
