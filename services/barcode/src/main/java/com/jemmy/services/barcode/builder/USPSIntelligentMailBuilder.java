package com.jemmy.services.barcode.builder;

import com.jemmy.services.barcode.model.dto.barcode.BarcodeRequestDto;
import com.jemmy.services.barcode.model.enums.CheckSumModeEnum;
import com.jemmy.services.barcode.model.enums.CodeTypeEnum;
import com.jemmy.services.barcode.model.enums.HumanReadablePlacementEnum;
import org.apache.avalon.framework.configuration.DefaultConfiguration;

/**
 * @author Jemmy
 */
public class USPSIntelligentMailBuilder extends AbstractCodeBuilder<USPSIntelligentMailBuilder>{


    public USPSIntelligentMailBuilder(DefaultConfiguration parentCfg) {
        super(parentCfg,CodeTypeEnum.USPS4CB);
        parentCfg.addChild(cfg);
    }

    public USPSIntelligentMailBuilder wideFactor(Float wideFactor){
        return buildNumberProperty("wide-factor",wideFactor,2.5f,Float.class);
    }

    public USPSIntelligentMailBuilder intercharGapWidth(String intercharGapWidth){
        return buildStringProperty("interchar-gap-width",intercharGapWidth,"025in");
    }

    public USPSIntelligentMailBuilder checkSum(CheckSumModeEnum checkSum){
        if(null==checkSum){
            checkSum=CheckSumModeEnum.AUTO;
        }
        return buildStringProperty("checksum",checkSum.getMode(),"auto");
    }

    public USPSIntelligentMailBuilder trackHeight(String trackHeight){
        return buildStringProperty("track-height",trackHeight,"0.050in");
    }

    public USPSIntelligentMailBuilder ascenderHeight(String ascenderHeight){
        return buildStringProperty("ascender-height",ascenderHeight,"0.050in");
    }



    @Override
    public DefaultConfiguration build(BarcodeRequestDto dto) {
        BarcodeRequestDto.BarCode barCode=dto.getBarCode();
        BarcodeRequestDto.Text text=dto.getText();
        this.moduleWidth(barCode.getModuleWidth(),"0.020in")
                .wideFactor(barCode.getWideFactor())
                .trackHeight(barCode.getTrackHeight())
                .ascenderHeight(barCode.getAscenderHeight())
                .intercharGapWidth(barCode.getIntercharGapWidth())
                .quietZone(barCode.getQuietZone(),true,"0.125in")
                .verticalQuietZone(barCode.getVerticalQuietZone(),true,"0.028in")
            .humanReadable()
                .placement(text.getPlacement(),HumanReadablePlacementEnum.NONE)
                .fontName(text.getFontName(),"Helvetica")
                .fontSize(text.getFontSize(),"8pt")
        ;
        return cfg;
    }
}
