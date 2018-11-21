package com.jemmy.services.barcode.service;

import com.jemmy.services.barcode.model.enums.CodeTypeEnum;
import org.krysalis.barcode4j.DefaultBarcodeClassResolver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Jemmy
 */
@Component
public class BarcodeClassResolverService {
    private DefaultBarcodeClassResolver resolver;
    public BarcodeClassResolverService(){
        resolver=new DefaultBarcodeClassResolver();
    }

    @PostConstruct
    public void init(){
        resolver.registerBarcodeClass(CodeTypeEnum.CODE128.getType(), "io.brant.phantomjs.example.barcode.code.code128.ExtCode128", true);
        resolver.registerBarcodeClass(CodeTypeEnum.CODE39.getType(),"io.brant.phantomjs.example.barcode.code.code39.ExtCode39",true);
        resolver.registerBarcodeClass(CodeTypeEnum.CODABAR.getType(), "io.brant.phantomjs.example.barcode.code.codabar.ExtCodabar", true);
        resolver.registerBarcodeClass(CodeTypeEnum.INTL2OF5.getType(), "io.brant.phantomjs.example.barcode.code.int2of5.ExtInterleaved2Of5", true);
        resolver.registerBarcodeClass(CodeTypeEnum.ROYALMAILCBC.getType(), "io.brant.phantomjs.example.barcode.code.fourstate.ExtRoyalMailCBC", true);
        resolver.registerBarcodeClass(CodeTypeEnum.USPS4CB.getType(), "io.brant.phantomjs.example.barcode.code.fourstate.ExtUSPSIntelligentMail", true);
        resolver.registerBarcodeClass(CodeTypeEnum.EAN8.getType(), "io.brant.phantomjs.example.barcode.code.upcean.ExtEAN8", true);
        resolver.registerBarcodeClass(CodeTypeEnum.EAN13.getType(), "io.brant.phantomjs.example.barcode.code.upcean.ExtEAN13", true);
        resolver.registerBarcodeClass(CodeTypeEnum.UPCA.getType(), "io.brant.phantomjs.example.barcode.code.upcean.ExtUPCA", true);
        resolver.registerBarcodeClass(CodeTypeEnum.UPCE.getType(), "io.brant.phantomjs.example.barcode.code.upcean.ExtUPCE", true);
        resolver.registerBarcodeClass(CodeTypeEnum.POSTNET.getType(), "io.brant.phantomjs.example.barcode.code.postnet.ExtPOSTNET", true);

    }

    public DefaultBarcodeClassResolver getResolver(){
        return resolver;
    }
}
