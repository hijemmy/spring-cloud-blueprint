package com.jemmy.services.barcode.code.fourstate;

import org.krysalis.barcode4j.TextAlignment;
import org.krysalis.barcode4j.impl.fourstate.USPSIntelligentMailBean;
import org.krysalis.barcode4j.impl.fourstate.USPSIntelligentMailLogicImpl;
import org.krysalis.barcode4j.output.Canvas;
import org.krysalis.barcode4j.output.CanvasProvider;

/**
 * @author Jemmy
 */
public class ExtUSPSIntelligentMailBean extends USPSIntelligentMailBean {

    public ExtUSPSIntelligentMailBean() {
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
        handler.setTextAlignment(TextAlignment.TA_LEFT);

        USPSIntelligentMailLogicImpl impl = new USPSIntelligentMailLogicImpl();
        impl.generateBarcodeLogic(handler, msg);
    }
}
