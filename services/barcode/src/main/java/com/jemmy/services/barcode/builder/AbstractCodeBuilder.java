package com.jemmy.services.barcode.builder;

import com.jemmy.services.barcode.model.dto.barcode.BarcodeRequestDto;
import com.jemmy.services.barcode.model.enums.CodeTypeEnum;
import org.apache.avalon.framework.configuration.DefaultConfiguration;
import org.springframework.util.StringUtils;

/**
 * @author Jemmy
 */
public abstract class AbstractCodeBuilder<B extends AbstractCodeBuilder>  {

    protected  DefaultConfiguration cfg;

    public AbstractCodeBuilder(DefaultConfiguration parentCfg, CodeTypeEnum t) {
        this.cfg = new DefaultConfiguration(t.getType());
        parentCfg.addChild(cfg);
    }

    /**
     * 构建字符串类型属性
     * @param pName
     * @param pValue
     * @param defValue
     * @return
     */
    protected B buildStringProperty(String pName,String pValue,String defValue){
        if(StringUtils.isEmpty(pValue)){
            pValue=defValue;
        }
        DefaultConfiguration attr=new DefaultConfiguration(pName);
        attr.setValue(pValue);
        cfg.addChild(attr);
        return (B)this;
    }

    /**
     * 构建布尔类型属性
     * @param pName
     * @param pValue
     * @param defValue
     * @return
     */
    protected B buildBooleanProperty(String pName,Boolean pValue,Boolean defValue){
        if(null==pValue){
            pValue=defValue;
        }
        DefaultConfiguration attr=new DefaultConfiguration(pName);
        attr.setValue(pValue);
        cfg.addChild(attr);
        return (B)this;
    }

    /**
     * 构建数值类型属性
     * @param pName
     * @param pValue
     * @param defValue
     * @param clazz
     * @param <N>
     * @return
     */
    protected <N extends Number> B buildNumberProperty(String pName,N pValue,N defValue,Class<N> clazz){
        if(null==pValue){
            pValue=defValue;
        }
        DefaultConfiguration attr=new DefaultConfiguration(pName);
        if(clazz.equals(Integer.class)){
            attr.setValue(pValue.intValue());
        }else if(clazz.equals(Float.class)){
            attr.setValue(pValue.floatValue());
        }else if(clazz.equals(Long.class)){
            attr.setValue(pValue.longValue());
        }
        cfg.addChild(attr);
        return (B)this;
    }

    public abstract DefaultConfiguration build(BarcodeRequestDto dto);
    public  B quietZone(String quietZone,boolean enable,String defValue){
        DefaultConfiguration attr=new DefaultConfiguration("quiet-zone");
        if(!enable){
            attr.setAttribute("enabled",false);
        }else {
            attr.setAttribute("enabled",true);
            if(StringUtils.isEmpty(quietZone)){
                quietZone=defValue;
            }
            attr.setValue(quietZone);
        }

        cfg.addChild(attr);
        return (B)this;
    }

    public  B verticalQuietZone(String verticalQuietZone,boolean enable,String defValue){
        DefaultConfiguration attr=new DefaultConfiguration("vertical-quiet-zone");
        if(!enable){
            attr.setAttribute("enabled",false);
        }else {
            attr.setAttribute("enabled",true);
            if(StringUtils.isEmpty(verticalQuietZone)){
                verticalQuietZone=defValue;
            }
            attr.setValue(verticalQuietZone);
        }

        cfg.addChild(attr);
        return (B)this;
    }

    public B height(String height){
        return buildStringProperty("height",height,"15mm");
    }

    public TextBuilder humanReadable(){
        DefaultConfiguration attr=new DefaultConfiguration("human-readable");
        cfg.addChild(attr);
        return new TextBuilder(this,attr);
    }

    public B moduleWidth(String moduleWidth,String defValue){
        return buildStringProperty("module-width",moduleWidth,defValue);
    }


}
