package com.jemmy.services.barcode.builder;

import com.jemmy.services.barcode.model.dto.barcode.BarcodeRequestDto;
import com.jemmy.services.barcode.model.enums.CheckSumModeEnum;
import com.jemmy.services.barcode.model.enums.CodeTypeEnum;
import org.apache.avalon.framework.configuration.DefaultConfiguration;

/**
 * @author Jemmy
 */
public class RoyalMailCBCBuilder extends AbstractCodeBuilder<RoyalMailCBCBuilder>{


    public RoyalMailCBCBuilder(DefaultConfiguration parentCfg) {
        super(parentCfg,CodeTypeEnum.ROYALMAILCBC);
        parentCfg.addChild(cfg);
    }

    public RoyalMailCBCBuilder wideFactor(Float wideFactor){
        return buildNumberProperty("wide-factor",wideFactor,2.5f,Float.class);
    }

    public RoyalMailCBCBuilder intercharGapWidth(String intercharGapWidth){
        return buildStringProperty("interchar-gap-width",intercharGapWidth,"1mw");
    }

    public RoyalMailCBCBuilder checkSum(CheckSumModeEnum checkSum){
        if(null==checkSum){
            checkSum=CheckSumModeEnum.AUTO;
        }
        return buildStringProperty("checksum",checkSum.getMode(),"auto");
    }

    public RoyalMailCBCBuilder trackHeight(String trackHeight){
        return buildStringProperty("track-height",trackHeight,"1.25mm");
    }

    public RoyalMailCBCBuilder ascenderHeight(String ascenderHeight){
        return buildStringProperty("ascender-height",ascenderHeight,"1.25mm");
    }



    @Override
    public DefaultConfiguration build(BarcodeRequestDto dto) {
        BarcodeRequestDto.BarCode barCode=dto.getBarCode();
        this.moduleWidth(barCode.getModuleWidth(),"0.53mm")
                .wideFactor(barCode.getWideFactor())
                .trackHeight(barCode.getTrackHeight())
                .ascenderHeight(barCode.getAscenderHeight())
                .intercharGapWidth(barCode.getIntercharGapWidth())
                .quietZone(barCode.getQuietZone(),true,"10mw")
                .checkSum(barCode.getCheckSum());
        return cfg;
    }
}
