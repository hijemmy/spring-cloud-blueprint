package com.jemmy.services.barcode.code.postnet;

import org.krysalis.barcode4j.impl.postnet.POSTNETBean;
import org.krysalis.barcode4j.impl.postnet.POSTNETLogicImpl;
import org.krysalis.barcode4j.output.Canvas;
import org.krysalis.barcode4j.output.CanvasProvider;

public class ExtPOSTNETBean extends POSTNETBean {

    public ExtPOSTNETBean() {
        super();
    }

    @Override
    public void generateBarcode(CanvasProvider canvas, String msg) {
        if ((msg == null)
                || (msg.length() == 0)) {
            throw new NullPointerException("Parameter msg must not be empty");
        }

        ExtPOSTNETLogicHandler handler =
                new ExtPOSTNETLogicHandler(this, new Canvas(canvas),canvas);

        POSTNETLogicImpl impl = new POSTNETLogicImpl(
                getChecksumMode(), isDisplayChecksum());
        impl.generateBarcodeLogic(handler, msg);
    }

}
