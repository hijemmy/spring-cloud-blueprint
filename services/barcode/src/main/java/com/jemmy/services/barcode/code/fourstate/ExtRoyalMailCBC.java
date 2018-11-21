package com.jemmy.services.barcode.code.fourstate;

import org.krysalis.barcode4j.impl.fourstate.RoyalMailCBC;

public class ExtRoyalMailCBC extends RoyalMailCBC {

    public ExtRoyalMailCBC() {
        this.bean=new ExtRoyalMailCBCBean();
    }
}
