package com.jemmy.services.barcode.model.enums;

public enum BaselineAlignmentEnum {
    BOTTOM("bottom"),
    TOP("top");

    private String mode;

    BaselineAlignmentEnum(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
