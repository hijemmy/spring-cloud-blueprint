package com.jemmy.services.barcode.code.codabar;

import com.jemmy.services.barcode.ExtCanvasLogicHandler;
import lombok.extern.slf4j.Slf4j;
import org.krysalis.barcode4j.ClassicBarcodeLogicHandler;
import org.krysalis.barcode4j.impl.codabar.CodabarBean;
import org.krysalis.barcode4j.impl.codabar.CodabarLogicImpl;
import org.krysalis.barcode4j.output.Canvas;
import org.krysalis.barcode4j.output.CanvasProvider;

/**
 * @author Jemmy
 */
@Slf4j
public class ExtCodabarBean extends CodabarBean {

    public ExtCodabarBean() {
        super();
    }

    @Override
    public void generateBarcode(final CanvasProvider canvasProvider, String msg) {
        if ((msg == null) || (msg.length() == 0)) {
            throw new NullPointerException("Parameter msg must not be empty");
        }

        ClassicBarcodeLogicHandler handler =new ExtCanvasLogicHandler(this, new Canvas(canvasProvider),canvasProvider);

        CodabarLogicImpl impl = new CodabarLogicImpl(getChecksumMode(), isDisplayStartStop());
        impl.generateBarcodeLogic(handler, msg);
    }
}
