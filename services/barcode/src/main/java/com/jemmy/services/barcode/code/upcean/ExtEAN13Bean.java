package com.jemmy.services.barcode.code.upcean;

import org.krysalis.barcode4j.impl.upcean.EAN13LogicImpl;
import org.krysalis.barcode4j.impl.upcean.UPCEANLogicImpl;

public class ExtEAN13Bean extends ExtUPCEANBean{
    @Override
    public UPCEANLogicImpl createLogicImpl() {
        return new EAN13LogicImpl(getChecksumMode());
    }
}
