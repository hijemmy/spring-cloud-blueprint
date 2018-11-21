package com.jemmy.services.barcode.builder;

import com.jemmy.services.barcode.model.dto.BarcodeRequestDto;
import com.jemmy.services.barcode.model.enums.CheckSumModeEnum;
import com.jemmy.services.barcode.model.enums.CodeTypeEnum;
import com.jemmy.services.barcode.model.enums.HumanReadablePlacementEnum;
import org.apache.avalon.framework.configuration.DefaultConfiguration;

/**
 * @author Jemmy
 */
public class Code39Builder extends AbstractCodeBuilder<Code39Builder>{


    public Code39Builder(DefaultConfiguration parentCfg) {
        super(parentCfg,CodeTypeEnum.CODE39);
        parentCfg.addChild(cfg);
    }

    public Code39Builder wideFactor(Float wideFactor){
        return buildNumberProperty("wide-factor",wideFactor,2.5f,Float.class);
    }

    public Code39Builder intercharGapWidth(String intercharGapWidth){
        return buildStringProperty("interchar-gap-width",intercharGapWidth,"1mw");
    }

    public Code39Builder checkSum(CheckSumModeEnum checkSum){
        if(null==checkSum){
            checkSum=CheckSumModeEnum.AUTO;
        }
        return buildStringProperty("checksum",checkSum.getMode(),"auto");
    }

    @Override
    public DefaultConfiguration build(BarcodeRequestDto dto) {
        this.height(dto.getBarBorder().getHeight())
                .moduleWidth(dto.getBarCode().getModuleWidth(),"0.19mm")
                .wideFactor(dto.getBarCode().getWideFactor())
                .intercharGapWidth(dto.getBarCode().getIntercharGapWidth())
                .quietZone(dto.getBarCode().getQuietZone(),true,"10mw")
                .checkSum(dto.getBarCode().getCheckSum())
                .humanReadable()
                    .placement(dto.getText().getPlacement(),HumanReadablePlacementEnum.BOTTOM)
                    .fontName(dto.getText().getFontName(),"Helvetica")
                    .displayStartStop(dto.getText().getDisplayStartStop(),false)
                    .displayChecksum(dto.getText().getDisplayChecksum(),false)
                    .fontSize(dto.getText().getFontSize(),"8pt").end();
        return cfg;
    }
}
