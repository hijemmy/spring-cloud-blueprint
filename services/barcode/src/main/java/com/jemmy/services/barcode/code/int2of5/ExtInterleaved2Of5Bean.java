package com.jemmy.services.barcode.code.int2of5;

import com.jemmy.services.barcode.code.ExtCanvasLogicHandler;
import org.krysalis.barcode4j.ClassicBarcodeLogicHandler;
import org.krysalis.barcode4j.impl.int2of5.Interleaved2Of5Bean;
import org.krysalis.barcode4j.impl.int2of5.Interleaved2Of5LogicImpl;
import org.krysalis.barcode4j.output.Canvas;
import org.krysalis.barcode4j.output.CanvasProvider;

public class ExtInterleaved2Of5Bean extends Interleaved2Of5Bean {

    public ExtInterleaved2Of5Bean() {
        super();
    }

    @Override
    public void generateBarcode(CanvasProvider canvas, String msg) {
        if ((msg == null)
                || (msg.length() == 0)) {
            throw new NullPointerException("Parameter msg must not be empty");
        }

        ClassicBarcodeLogicHandler handler =
                new ExtCanvasLogicHandler(this, new Canvas(canvas),canvas);
        //handler = new LoggingLogicHandlerProxy(handler);

        Interleaved2Of5LogicImpl impl = new Interleaved2Of5LogicImpl(
                getChecksumMode(), isDisplayChecksum());
        impl.generateBarcodeLogic(handler, msg);
    }
}
