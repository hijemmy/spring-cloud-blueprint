package com.jemmy.services.barcode.builder;

import com.jemmy.services.barcode.model.enums.HumanReadablePlacementEnum;
import org.apache.avalon.framework.configuration.DefaultConfiguration;
import org.springframework.util.StringUtils;

/**
 * @author Jemmy
 */
public class TextBuilder{

    private DefaultConfiguration humanReadable;
    private AbstractCodeBuilder parent;

    public TextBuilder(AbstractCodeBuilder parent,DefaultConfiguration humanReadable) {
        this.parent=parent;
        this.humanReadable = humanReadable;
    }

    public TextBuilder fontSize(String fontSize,String defValue){
        if(StringUtils.isEmpty(fontSize)){
            fontSize=defValue;
        }
        DefaultConfiguration attr = new DefaultConfiguration("font-size");
        attr.setValue(fontSize);
        humanReadable.addChild(attr);
        return this;
    }

    public TextBuilder fontName(String fontName,String defValue){
        if(StringUtils.isEmpty(fontName)){
            fontName=defValue;
        }
        DefaultConfiguration attr = new DefaultConfiguration("font-name");
        attr.setValue(fontName);
        humanReadable.addChild(attr);
        return this;
    }

    public TextBuilder placement(HumanReadablePlacementEnum placement,HumanReadablePlacementEnum defValue){
        if(null==placement){
            placement=defValue;
        }

        DefaultConfiguration attr = new DefaultConfiguration("placement");
        attr.setValue(placement.getPlacement());
        humanReadable.addChild(attr);
        return this;
    }

    public TextBuilder displayStartStop(Boolean displayStartStop,boolean defValue){
        DefaultConfiguration attr = new DefaultConfiguration("display-start-stop");

        if(null==displayStartStop){
            displayStartStop=defValue;
        }
        attr.setValue(displayStartStop);
        humanReadable.addChild(attr);
        return this;
    }

    public TextBuilder displayChecksum(Boolean displayChecksum,boolean defValue){
        DefaultConfiguration attr = new DefaultConfiguration("display-checksum");

        if(null==displayChecksum){
            displayChecksum=defValue;
        }
        attr.setValue(displayChecksum);
        humanReadable.addChild(attr);
        return this;
    }

    public AbstractCodeBuilder end(){
        return parent;
    }
}
