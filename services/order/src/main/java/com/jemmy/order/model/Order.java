package com.jemmy.order.model;

import com.jemmy.common.model.BaseModel;
import java.math.BigDecimal;

public class Order extends BaseModel {
    private Long id;

    private Long uid;

    private BigDecimal total;

    private Byte state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}