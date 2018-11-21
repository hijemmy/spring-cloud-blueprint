package com.jemmy.services.barcode.builder;

import com.jemmy.services.barcode.model.dto.BarcodeRequestDto;
import com.jemmy.services.barcode.model.enums.CodeTypeEnum;
import com.jemmy.services.barcode.model.enums.HumanReadablePlacementEnum;
import org.apache.avalon.framework.configuration.DefaultConfiguration;

/**
 * @author Jemmy
 */
public class CodabarBuilder extends AbstractCodeBuilder<CodabarBuilder>{


    public CodabarBuilder(DefaultConfiguration parentCfg) {
        super(parentCfg,CodeTypeEnum.CODABAR);
        parentCfg.addChild(cfg);
    }

    public CodabarBuilder wideFactor(Float wideFactor){
        return buildNumberProperty("wide-factor",wideFactor,2.5f,Float.class);
    }


    @Override
    public DefaultConfiguration build(BarcodeRequestDto dto) {
        this.height(dto.getBarBorder().getHeight())
                .moduleWidth(dto.getBarCode().getModuleWidth(),"0.21mm")
                .wideFactor(dto.getBarCode().getWideFactor())
                .quietZone(dto.getBarCode().getQuietZone(),true,"10mw")
                .humanReadable()
                    .placement(dto.getText().getPlacement(),HumanReadablePlacementEnum.BOTTOM)
                    .fontName(dto.getText().getFontName(),"Helvetica")
                    .displayStartStop(dto.getText().getDisplayStartStop(),false)
                    .fontSize(dto.getText().getFontSize(),"8pt").end();
        return cfg;
    }
}
