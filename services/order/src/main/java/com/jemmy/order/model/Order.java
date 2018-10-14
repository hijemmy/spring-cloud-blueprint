package com.jemmy.order.model;

import com.jemmy.common.model.AbstractBaseModel;
import java.math.BigDecimal;
import javax.persistence.*;

public class Order extends AbstractBaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 买家
     */
    private Long uid;

    /**
     * 总价款
     */
    private BigDecimal total;

    private Byte state;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取买家
     *
     * @return uid - 买家
     */
    public Long getUid() {
        return uid;
    }

    /**
     * 设置买家
     *
     * @param uid 买家
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * 获取总价款
     *
     * @return total - 总价款
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * 设置总价款
     *
     * @param total 总价款
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return state
     */
    public Byte getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(Byte state) {
        this.state = state;
    }
}