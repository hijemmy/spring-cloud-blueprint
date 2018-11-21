package com.jemmy.services.barcode.code.int2of5;

import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.int2of5.ITF14;

public class ExtITF14 extends ITF14 {

    public ExtITF14() {
        super();
    }

    @Override
    protected AbstractBarcodeBean createBean() {
        return new ExtITF14Bean();
    }
}
