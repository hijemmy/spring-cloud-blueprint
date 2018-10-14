package com.jemmy.product.model;

import com.jemmy.common.model.AbstractBaseModel;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
@EqualsAndHashCode(of="id")
public class Product extends AbstractBaseModel {
    private Long id;

    private String name;

    private BigDecimal price;

    private Integer num;

    private Byte state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}