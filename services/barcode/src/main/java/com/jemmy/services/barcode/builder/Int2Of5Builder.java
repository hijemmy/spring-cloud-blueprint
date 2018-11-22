package com.jemmy.services.barcode.builder;

import com.jemmy.services.barcode.model.dto.barcode.BarcodeRequestDto;
import com.jemmy.services.barcode.model.enums.CheckSumModeEnum;
import com.jemmy.services.barcode.model.enums.CodeTypeEnum;
import com.jemmy.services.barcode.model.enums.HumanReadablePlacementEnum;
import org.apache.avalon.framework.configuration.DefaultConfiguration;

/**
 * @author Jemmy
 */
public class Int2Of5Builder extends AbstractCodeBuilder<Int2Of5Builder>{


    public Int2Of5Builder(DefaultConfiguration parentCfg) {
        super(parentCfg,CodeTypeEnum.INTL2OF5);
        parentCfg.addChild(cfg);
    }

    public Int2Of5Builder wideFactor(Float wideFactor){
        return buildNumberProperty("wide-factor",wideFactor,2.5f,Float.class);
    }

    public Int2Of5Builder checkSum(CheckSumModeEnum checkSum){
        if(null==checkSum){
            checkSum=CheckSumModeEnum.AUTO;
        }
        return buildStringProperty("checksum",checkSum.getMode(),"auto");
    }

    @Override
    public DefaultConfiguration build(BarcodeRequestDto dto) {
        this.height(dto.getBarBorder().getHeight())
                .moduleWidth(dto.getBarCode().getModuleWidth(),"0.21mm")
                .wideFactor(dto.getBarCode().getWideFactor())
                .quietZone(dto.getBarCode().getQuietZone(),true,"10mw")
                .checkSum(dto.getBarCode().getCheckSum())
                .humanReadable()
                    .placement(dto.getText().getPlacement(),HumanReadablePlacementEnum.BOTTOM)
                    .fontName(dto.getText().getFontName(),"Helvetica")
                    .displayChecksum(dto.getText().getDisplayChecksum(),false)
                    .fontSize(dto.getText().getFontSize(),"8pt").end();
        return cfg;
    }
}
