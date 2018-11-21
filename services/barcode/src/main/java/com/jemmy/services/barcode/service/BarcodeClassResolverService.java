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
        resolver.registerBarcodeClass(CodeTypeEnum.CODE128.getType(), "com.jemmy.services.barcode.code.code128.ExtCode128", true);
        resolver.registerBarcodeClass(CodeTypeEnum.CODE39.getType(),"com.jemmy.services.barcode.code.code39.ExtCode39",true);
        resolver.registerBarcodeClass(CodeTypeEnum.CODABAR.getType(), "com.jemmy.services.barcode.code.codabar.ExtCodabar", true);
        resolver.registerBarcodeClass(CodeTypeEnum.INTL2OF5.getType(), "com.jemmy.services.barcode.code.int2of5.ExtInterleaved2Of5", true);
        resolver.registerBarcodeClass(CodeTypeEnum.ROYALMAILCBC.getType(), "com.jemmy.services.barcode.code.fourstate.ExtRoyalMailCBC", true);
        resolver.registerBarcodeClass(CodeTypeEnum.USPS4CB.getType(), "com.jemmy.services.barcode.code.fourstate.ExtUSPSIntelligentMail", true);
        resolver.registerBarcodeClass(CodeTypeEnum.EAN8.getType(), "com.jemmy.services.barcode.code.upcean.ExtEAN8", true);
        resolver.registerBarcodeClass(CodeTypeEnum.EAN13.getType(), "com.jemmy.services.barcode.code.upcean.ExtEAN13", true);
        resolver.registerBarcodeClass(CodeTypeEnum.UPCA.getType(), "com.jemmy.services.barcode.code.upcean.ExtUPCA", true);
        resolver.registerBarcodeClass(CodeTypeEnum.UPCE.getType(), "com.jemmy.services.barcode.code.upcean.ExtUPCE", true);
        resolver.registerBarcodeClass(CodeTypeEnum.POSTNET.getType(), "com.jemmy.services.barcode.code.postnet.ExtPOSTNET", true);

    }

    public DefaultBarcodeClassResolver getResolver(){
        return resolver;
    }
}
