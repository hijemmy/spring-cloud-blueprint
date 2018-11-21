package com.jemmy.services.barcode.code.fourstate;

import org.krysalis.barcode4j.impl.fourstate.USPSIntelligentMail;

public class ExtUSPSIntelligentMail extends USPSIntelligentMail {

    public ExtUSPSIntelligentMail() {
        this.bean=new ExtUSPSIntelligentMailBean();
    }
}
