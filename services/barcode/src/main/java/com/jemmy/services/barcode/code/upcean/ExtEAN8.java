package com.jemmy.services.barcode.code.upcean;

import org.krysalis.barcode4j.impl.upcean.EAN8;

public class ExtEAN8 extends EAN8 {

    public ExtEAN8() {
        this.bean=new ExtEAN8Bean();
    }
}
