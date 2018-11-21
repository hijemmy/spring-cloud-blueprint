package com.jemmy.services.barcode.code.upcean;

import org.krysalis.barcode4j.BarcodeDimension;
import org.krysalis.barcode4j.impl.upcean.EAN8LogicImpl;
import org.krysalis.barcode4j.impl.upcean.UPCEANLogicImpl;

public class ExtEAN8Bean extends ExtUPCEANBean{
    /** @see org.krysalis.barcode4j.impl.upcean.UPCEAN */
    @Override
    public UPCEANLogicImpl createLogicImpl() {
        return new EAN8LogicImpl(getChecksumMode());
    }

    /** @see org.krysalis.barcode4j.impl.upcean.UPCEAN */
    @Override
    public BarcodeDimension calcDimensions(String msg) {
        double width = 3 * moduleWidth; //left guard
        width += 4 * 7 * moduleWidth;
        width += 5 * moduleWidth; //center guard
        width += 4 * 7 * moduleWidth;
        width += 3 * moduleWidth; //right guard
        width += supplementalWidth(msg);
        final double qz = (hasQuietZone() ? quietZone : 0);
        return new BarcodeDimension(width, getHeight(),
                width + (2 * qz), getHeight(),
                quietZone, 0.0);
    }
}
