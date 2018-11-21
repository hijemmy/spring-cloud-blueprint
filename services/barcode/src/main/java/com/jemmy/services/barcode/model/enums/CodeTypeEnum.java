package com.jemmy.services.barcode.model.enums;

/**
 * 编码类型枚举
 */
public enum CodeTypeEnum {
    CODE39("code39"),
    CODE128("code128"),
    CODABAR("codabar"),
    DATAMATRIX("datamatrix"),
    ROYALMAILCBC("royal-mail-cbc"),
    EAN8("ean-8"),
    EAN13("ean-13"),
    EAN128("ean-128"),
    UPCE("upc-e"),
    UPCA("upc-a"),
    PDF417("pdf417"),
    ITF14("itf-14"),
    INTL2OF5("intl2of5"),
    USPS4CB("usps4cb"),
    POSTNET("postnet");
    private String type;

    CodeTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
