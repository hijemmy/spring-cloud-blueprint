package com.jemmy.services.barcode.builder;

import com.jemmy.services.barcode.model.dto.barcode.BarcodeRequestDto;
import com.jemmy.services.barcode.model.enums.CodeTypeEnum;
import com.jemmy.services.barcode.model.enums.HumanReadablePlacementEnum;
import org.apache.avalon.framework.configuration.DefaultConfiguration;

/**
 * @author Jemmy
 */
public class Code128Builder extends AbstractCodeBuilder<Code128Builder>{


    public Code128Builder(DefaultConfiguration parentCfg) {
        super(parentCfg,CodeTypeEnum.CODE128);
        parentCfg.addChild(cfg);
    }

    public Code128Builder codesets(String codeSets){
        return buildStringProperty("codesets",codeSets,"ABC");
    }

    @Override
    public DefaultConfiguration build(BarcodeRequestDto dto) {
        this.height(dto.getBarBorder().getHeight())
            .moduleWidth(dto.getBarCode().getModuleWidth(),"0.21mm")
            .quietZone(dto.getBarCode().getQuietZone(),true,"10mw")
            .humanReadable()
                .placement(dto.getText().getPlacement(),HumanReadablePlacementEnum.BOTTOM)
                .fontName(dto.getText().getFontName(),"Helvetica")
                .fontSize(dto.getText().getFontSize(),"8pt").end();
        return cfg;
    }
}
