package com.jemmy.services.barcode.code.code128;

import com.jemmy.services.barcode.code.ExtCanvasLogicHandler;
import lombok.extern.slf4j.Slf4j;
import org.krysalis.barcode4j.ClassicBarcodeLogicHandler;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code128.Code128LogicImpl;
import org.krysalis.barcode4j.output.Canvas;
import org.krysalis.barcode4j.output.CanvasProvider;

/**
 * @author Jemmy
 */
@Slf4j
public class ExtCode128Bean extends Code128Bean {

    public ExtCode128Bean() {
        super();
    }

    @Override
    public void generateBarcode(final CanvasProvider canvasProvider, String msg) {
        if ((msg == null) || (msg.length() == 0)) {
            throw new NullPointerException("Parameter msg must not be empty");
        }

        ClassicBarcodeLogicHandler handler =new ExtCanvasLogicHandler(this, new Canvas(canvasProvider),canvasProvider);

        Code128LogicImpl impl = createLogicImpl();
        impl.generateBarcodeLogic(handler, msg);
    }
    private Code128LogicImpl createLogicImpl() {
        return new Code128LogicImpl(getCodeset());
    }
}
