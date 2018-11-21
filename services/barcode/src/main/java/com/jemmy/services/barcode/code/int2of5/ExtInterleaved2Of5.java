package com.jemmy.services.barcode.code.int2of5;

import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.int2of5.Interleaved2Of5;

public class ExtInterleaved2Of5 extends Interleaved2Of5 {

    public ExtInterleaved2Of5() {
        super();
    }

    @Override
    protected AbstractBarcodeBean createBean() {
        return new ExtInterleaved2Of5Bean();
    }
}
