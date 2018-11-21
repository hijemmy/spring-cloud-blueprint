package com.jemmy.services.barcode.util;

public interface BarCode4jUtil {

    /**
     * length支持的格式
     */
    final String REG_LENGTH="[1-9]+(\\.)?\\d*(mm|cm|pt)?";
    /**
     * module_width支持的格式
     */
    final String REG_LENGTH_MODULE_WIDTH="[1-9]+(\\.)?\\d*(mm|cm|pt|mw)?";

}
