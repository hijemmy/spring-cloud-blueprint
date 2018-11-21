package com.jemmy.services.barcode.builder;

import com.jemmy.services.barcode.model.dto.BarcodeRequestDto;
import com.jemmy.services.barcode.model.enums.BaselineAlignmentEnum;
import com.jemmy.services.barcode.model.enums.CheckSumModeEnum;
import com.jemmy.services.barcode.model.enums.CodeTypeEnum;
import com.jemmy.services.barcode.model.enums.HumanReadablePlacementEnum;
import org.apache.avalon.framework.configuration.DefaultConfiguration;

/**
 * @author Jemmy
 */
public class POSTNETBuilder extends AbstractCodeBuilder<POSTNETBuilder>{


    public POSTNETBuilder(DefaultConfiguration parentCfg) {
        super(parentCfg,CodeTypeEnum.POSTNET);
        parentCfg.addChild(cfg);
    }


    public POSTNETBuilder intercharGapWidth(String intercharGapWidth){
        return buildStringProperty("interchar-gap-width",intercharGapWidth,"0.026in");
    }

    public POSTNETBuilder checkSum(CheckSumModeEnum checkSum){
        if(null==checkSum){
            checkSum=CheckSumModeEnum.AUTO;
        }
        return buildStringProperty("checksum",checkSum.getMode(),CheckSumModeEnum.AUTO.getMode());
    }

    public POSTNETBuilder baselineAlignment(BaselineAlignmentEnum baselineAlignment){
        if(null==baselineAlignment){
            baselineAlignment=BaselineAlignmentEnum.BOTTOM;
        }
        return buildStringProperty("baseline-alignment",baselineAlignment.getMode(),BaselineAlignmentEnum.BOTTOM.getMode());
    }

    public POSTNETBuilder tallBarHeight(String tallBarHeight){
        return buildStringProperty("tall-bar-height",tallBarHeight,"0.125in");
    }

    public POSTNETBuilder shortBarHeight(String shortBarHeight){
        return buildStringProperty("short-bar-height",shortBarHeight,"0.050in");
    }



    @Override
    public DefaultConfiguration build(BarcodeRequestDto dto) {
        BarcodeRequestDto.BarCode barCode=dto.getBarCode();
        BarcodeRequestDto.Text text=dto.getText();
        this.height(dto.getBarBorder().getHeight())
                .moduleWidth(barCode.getModuleWidth(),"0.53mm")
                .quietZone(barCode.getQuietZone(),true,"10mw")
                .tallBarHeight(barCode.getTallBarHeight())
                .shortBarHeight(barCode.getShortBarHeight())
                .intercharGapWidth(barCode.getIntercharGapWidth())
                .quietZone(barCode.getQuietZone(),true,"10mw")
                .baselineAlignment(barCode.getBaselineAlignment())
                .checkSum(barCode.getCheckSum())
                .humanReadable()
                    .placement(text.getPlacement(),HumanReadablePlacementEnum.NONE)
                    .fontName(text.getFontName(),"Helvetica")
                    .displayChecksum(text.getDisplayChecksum(),true)
                    .fontSize(text.getFontSize(),"8pt").end()
        ;
        return cfg;
    }
}
