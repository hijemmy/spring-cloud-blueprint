package com.jemmy.services.barcode.code.postnet;

import org.krysalis.barcode4j.impl.postnet.POSTNET;

public class ExtPOSTNET extends POSTNET {
    public ExtPOSTNET() {
        this.bean=new ExtPOSTNETBean();
    }
}
