package com.jemmy.services.barcode.builder;

import com.jemmy.services.barcode.model.dto.BarcodeRequestDto;
import com.jemmy.services.barcode.model.enums.CodeTypeEnum;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfiguration;

/**
 * @author Jemmy
 */
public class ConfigurationBuilder{

    public static final CodeTypeEnum DEFAULT_BARCODE_TYPE=CodeTypeEnum.CODE128;
    private DefaultConfiguration cfg;

    public ConfigurationBuilder() {
        cfg= new DefaultConfiguration("barcode");
    }


    public Configuration build(BarcodeRequestDto dto){
        CodeTypeEnum type=dto.getBarCode().getType();
        if(null==type){
            type=DEFAULT_BARCODE_TYPE;
        }
        switch (type){
            case CODE39:
                return new Code39Builder(cfg).build(dto);
            case CODABAR:
                return new CodabarBuilder(cfg).build(dto);
            case INTL2OF5:
                return new Int2Of5Builder(cfg).build(dto);
            case ROYALMAILCBC:
                return new RoyalMailCBCBuilder(cfg).build(dto);
            case USPS4CB:
                return new USPSIntelligentMailBuilder(cfg).build(dto);
            case EAN8:
                return new EAN8Builder(cfg).build(dto);
            case EAN13:
                return new EAN13Builder(cfg).build(dto);
            case UPCA:
                return new UPCABuilder(cfg).build(dto);
            case UPCE:
                return new UPCEBuilder(cfg).build(dto);
            case POSTNET:
                return new POSTNETBuilder(cfg).build(dto);
            case CODE128:
            default:
                return new Code128Builder(cfg).build(dto);
        }
    }
}
