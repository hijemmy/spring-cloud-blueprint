package com.jemmy.services.barcode.code.fourstate;

import org.krysalis.barcode4j.impl.fourstate.RoyalMailCBCBean;
import org.krysalis.barcode4j.impl.fourstate.RoyalMailCBCLogicImpl;
import org.krysalis.barcode4j.output.Canvas;
import org.krysalis.barcode4j.output.CanvasProvider;

public class ExtRoyalMailCBCBean extends RoyalMailCBCBean {

    public ExtRoyalMailCBCBean() {
        super();
    }

    @Override
    public void generateBarcode(CanvasProvider canvas, String msg) {
        if ((msg == null)
                || (msg.length() == 0)) {
            throw new NullPointerException("Parameter msg must not be empty");
        }

        ExtFourStateLogicHandler handler =
                new ExtFourStateLogicHandler(this, new Canvas(canvas),canvas);

        RoyalMailCBCLogicImpl impl = new RoyalMailCBCLogicImpl(
                getChecksumMode());
        impl.generateBarcodeLogic(handler, msg);
    }
}
