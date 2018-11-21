package com.jemmy.services.barcode.code.int2of5;

import com.jemmy.services.barcode.ExtCanvasLogicHandler;
import org.krysalis.barcode4j.ClassicBarcodeLogicHandler;
import org.krysalis.barcode4j.impl.int2of5.ITF14Bean;
import org.krysalis.barcode4j.impl.int2of5.ITF14LogicImpl;
import org.krysalis.barcode4j.output.Canvas;
import org.krysalis.barcode4j.output.CanvasProvider;

public class ExtITF14Bean extends ITF14Bean {

    public ExtITF14Bean() {
        super();
    }

    @Override
    public void generateBarcode(CanvasProvider canvas, String msg) {
        if ((msg == null)
                || (msg.length() == 0)) {
            throw new NullPointerException("Parameter msg must not be empty");
        }
        validate();

        ClassicBarcodeLogicHandler handler =
                new ExtCanvasLogicHandler(this, new Canvas(canvas),canvas);

        ITF14LogicImpl impl = new ITF14LogicImpl(
                getChecksumMode(), isDisplayChecksum());
        impl.generateBarcodeLogic(handler, msg);
    }
}
