package com.jemmy.common.model.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

/**
 * @author Jemmy
 */

public enum  OrderEventEnum {
    /**
     * 下订单
     */
    Add("order:add");
   private String businesstype;

    OrderEventEnum(String businesstype) {
        this.businesstype = businesstype;
    }
    @JsonValue
    public String getBusinesstype() {
        return businesstype;
    }

    @JsonCreator
    public static OrderEventEnum getItem(String businesstype){
        for(OrderEventEnum item : values()){
            if(item.getBusinesstype().equals(businesstype)){
                return item;
            }
        }
        return null;
    }

}
