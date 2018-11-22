package com.jemmy.services.barcode.builder;

import com.jemmy.services.barcode.model.dto.barcode.BarcodeRequestDto;
import com.jemmy.services.barcode.model.enums.CheckSumModeEnum;
import com.jemmy.services.barcode.model.enums.CodeTypeEnum;
import com.jemmy.services.barcode.model.enums.HumanReadablePlacementEnum;
import org.apache.avalon.framework.configuration.DefaultConfiguration;

/**
 * @author Jemmy
 */
public class UPCABuilder extends AbstractCodeBuilder<UPCABuilder>{


    public UPCABuilder(DefaultConfiguration parentCfg) {
        super(parentCfg,CodeTypeEnum.UPCA);
        parentCfg.addChild(cfg);
    }


    public UPCABuilder checkSum(CheckSumModeEnum checkSum){
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