package com.jemmy.services.barcode.builder;

import com.jemmy.services.barcode.model.dto.BarcodeRequestDto;
import com.jemmy.services.barcode.model.enums.CheckSumModeEnum;
import com.jemmy.services.barcode.model.enums.CodeTypeEnum;
import com.jemmy.services.barcode.model.enums.HumanReadablePlacementEnum;
import org.apache.avalon.framework.configuration.DefaultConfiguration;

/**
 * @author Jemmy
 */
public class Itf14Builder extends AbstractCodeBuilder<Itf14Builder>{


    public Itf14Builder(DefaultConfiguration parentCfg) {
        super(parentCfg,CodeTypeEnum.INTL2OF5);
        parentCfg.addChild(cfg);
    }

    public Itf14Builder wideFactor(Float wideFactor){
        return buildNumberProperty("wide-factor",wideFactor,2.5f,Float.class);
    }

    public Itf14Builder intercharGapWidth(String intercharGapWidth){
        return buildStringProperty("interchar-gap-width",intercharGapWidth,"1mw");
    }

    public Itf14Builder checkSum(CheckSumModeEnum checkSum){
        if(null==checkSum){
            checkSum=CheckSumModeEnum.AUTO;
        }
        return buildStringProperty("checksum",checkSum.getMode(),"auto");
    }

    public Itf14Builder bearerBarWidth(String bearerBarWidth){
        return buildStringProperty("bearer-bar-width",bearerBarWidth,"4.8mm");
    }

    public Itf14Builder bearerBox(Boolean bearerBox){
        return buildBooleanProperty("bearer-box",bearerBox,true);
    }

    @Override
    public DefaultConfiguration build(BarcodeRequestDto dto) {
        BarcodeRequestDto.BarCode barCode=dto.getBarCode();
        BarcodeRequestDto.Text text=dto.getText();
        this.height(dto.getBarBorder().getHeight())
                .moduleWidth(barCode.getModuleWidth(),"1.106mm")
                .wideFactor(barCode.getWideFactor())
                .quietZone(barCode.getQuietZone(),true,"10mw")
                .checkSum(barCode.getCheckSum())
                .bearerBarWidth(barCode.getBearerBarWidth())
                .bearerBox(barCode.getBearerBox())
                .humanReadable()
                    .placement(text.getPlacement(),HumanReadablePlacementEnum.BOTTOM)
                    .fontName(text.getFontName(),"Helvetica")
                    .displayChecksum(text.getDisplayChecksum(),true)
                    .fontSize(text.getFontSize(),"8pt").end();
        return cfg;
    }
}
