package com.jemmy.services.barcode.code.upcean;

import org.krysalis.barcode4j.impl.upcean.EAN13;

public class ExtEAN13 extends EAN13 {

    public ExtEAN13() {
        this.bean=new ExtEAN13Bean();
    }
}
