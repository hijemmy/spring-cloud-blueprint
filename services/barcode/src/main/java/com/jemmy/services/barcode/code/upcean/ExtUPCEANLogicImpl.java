package com.jemmy.services.barcode.code.upcean;

import org.krysalis.barcode4j.ChecksumMode;
import org.krysalis.barcode4j.impl.upcean.UPCEANLogicImpl;

public abstract class ExtUPCEANLogicImpl extends UPCEANLogicImpl {
    /**
     * Main constructor
     *
     * @param mode the checksum mode
     */
    public ExtUPCEANLogicImpl(ChecksumMode mode) {
        super(mode);
    }

    public static int getSupplementalLength(String msg) {
        String supp = retrieveSupplemental(msg);
        if (supp == null) {
            return 0;
        } else if (supp.length() == 2) {
            return 2;
        } else if (supp.length() == 5) {
            return 5;
        } else {
            throw new IllegalArgumentException(
                    "Illegal supplemental length (valid: 2 or 5): " + supp);
        }
    }

    public static String retrieveSupplemental(String msg) {
        int pos = msg.indexOf('+');
        if (pos >= 0) {
            return msg.substring(pos + 1);
        } else {
            return null;
        }
    }
}
