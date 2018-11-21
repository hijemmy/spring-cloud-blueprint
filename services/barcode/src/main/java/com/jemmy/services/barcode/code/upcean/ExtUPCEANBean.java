package com.jemmy.services.barcode.code.upcean;

import org.krysalis.barcode4j.ClassicBarcodeLogicHandler;
import org.krysalis.barcode4j.impl.upcean.UPCEANBean;
import org.krysalis.barcode4j.impl.upcean.UPCEANLogicImpl;
import org.krysalis.barcode4j.output.Canvas;
import org.krysalis.barcode4j.output.CanvasProvider;

public abstract class ExtUPCEANBean extends UPCEANBean {

    @Override
    public double supplementalWidth(String msg) {
        double width = 0;
        int suppLen = ExtUPCEANLogicImpl.getSupplementalLength(msg);
        if (suppLen > 0) {
            //Supplemental
            width += quietZone;
            width += 4 * moduleWidth; //left guard
            width += suppLen * 7 * moduleWidth;
            width += (suppLen - 1) * 2 * moduleWidth;
        }
        return width;
    }

    @Override
    public void generateBarcode(CanvasProvider canvas, String msg) {
        if ((msg == null) || (msg.length() == 0)) {
            throw new NullPointerException("Parameter msg must not be empty");
        }

        ClassicBarcodeLogicHandler handler = new ExtUPCEANCanvasLogicHandler(this, new Canvas(canvas),canvas);
        //handler = new LoggingLogicHandlerProxy(handler);

        UPCEANLogicImpl impl = createLogicImpl();
        impl.generateBarcodeLogic(handler, msg);
    }
}
