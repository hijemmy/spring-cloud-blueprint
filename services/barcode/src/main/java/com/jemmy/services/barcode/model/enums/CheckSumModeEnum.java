package com.jemmy.services.barcode.model.enums;

/**
 * @author Jemmy
 */

public enum CheckSumModeEnum {
    ADD("add"),
    CHECK("check"),
    IGNORE("ignore"),
    AUTO("auto");
    private String  mode;

    CheckSumModeEnum(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
