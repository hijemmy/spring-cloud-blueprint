package com.jemmy.services.barcode.builder;

import com.jemmy.services.barcode.model.dto.barcode.BarcodeRequestDto;
import com.jemmy.services.barcode.model.enums.CheckSumModeEnum;
import com.jemmy.services.barcode.model.enums.CodeTypeEnum;
import com.jemmy.services.barcode.model.enums.HumanReadablePlacementEnum;
import org.apache.avalon.framework.configuration.DefaultConfiguration;

/**
 * @author Jemmy
 */
public class EAN13Builder extends AbstractCodeBuilder<EAN13Builder>{


    public EAN13Builder(DefaultConfiguration parentCfg) {
        super(parentCfg,CodeTypeEnum.EAN13);
        parentCfg.addChild(cfg);
    }


    public EAN13Builder checkSum(CheckSumModeEnum checkSum){
        if(null==checkSum){
            checkSum=CheckSumModeEnum.AUTO;
        }
        return buildStringProperty("checksum",checkSum.getMode(),"auto");
    }

    @Override
    public DefaultConfiguration build(BarcodeRequestDto dto) {
        this.height(dto.getBarBorder().getHeight())
                .moduleWidth(dto.getBarCode().getModuleWidth(),"0.33mm")
                .quietZone(dto.getBarCode().getQuietZone(),true,"10mw")
                .checkSum(dto.getBarCode().getCheckSum())
                .humanReadable()
                    .placement(dto.getText().getPlacement(),HumanReadablePlacementEnum.BOTTOM)
                    .fontName(dto.getText().getFontName(),"Helvetica")
                    .fontSize(dto.getText().getFontSize(),"8pt").end();
        return cfg;
    }
}
