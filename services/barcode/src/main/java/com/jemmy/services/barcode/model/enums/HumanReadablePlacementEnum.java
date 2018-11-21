package com.jemmy.services.barcode.model.enums;

public enum HumanReadablePlacementEnum {
    /**
     * 不显示
     */
    NONE("none"),
    /**
     * 顶部
     */
    TOP("top"),
    /**
     * 底部
     */
    BOTTOM("bottom");

    private String placement;

    HumanReadablePlacementEnum(String placement) {
        this.placement = placement;
    }

    public String getPlacement() {
        return placement;
    }
}
