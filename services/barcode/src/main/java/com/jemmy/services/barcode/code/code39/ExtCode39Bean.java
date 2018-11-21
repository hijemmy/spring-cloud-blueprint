package com.jemmy.services.barcode.code.code39;

import com.jemmy.services.barcode.ExtCanvasLogicHandler;
import lombok.extern.slf4j.Slf4j;
import org.krysalis.barcode4j.ClassicBarcodeLogicHandler;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.impl.code39.Code39LogicImpl;
import org.krysalis.barcode4j.output.Canvas;
import org.krysalis.barcode4j.output.CanvasProvider;

/**
 * @author Jemmy
 */
@Slf4j
public class ExtCode39Bean extends Code39Bean {

    public ExtCode39Bean() {
        super();
    }

    @Override
    public void generateBarcode(final CanvasProvider canvasProvider, String msg) {
        if ((msg == null) || (msg.length() == 0)) {
            throw new NullPointerException("Parameter msg must not be empty");
        }

        ClassicBarcodeLogicHandler handler =new ExtCanvasLogicHandler(this, new Canvas(canvasProvider),canvasProvider);

        Code39LogicImpl impl = createLogicImpl();
        impl.generateBarcodeLogic(handler, msg);
    }
    private Code39LogicImpl createLogicImpl() {
        return new Code39LogicImpl(getChecksumMode(),
                isDisplayStartStop(), isDisplayChecksum(), isExtendedCharSetEnabled());
    }}
