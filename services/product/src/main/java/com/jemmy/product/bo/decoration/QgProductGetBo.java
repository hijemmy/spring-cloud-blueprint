package com.jemmy.product.bo.decoration;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Jemmy
 */
@Data
public class QgProductGetBo {
    private Long id;

    private Byte series;

    private Byte category;

    private String product_name;

    private BigDecimal weight;

    private BigDecimal processing_fee;

    private Integer tequan_value;

    private String img_url_1;

    private String img_url_2;

    private String img_url_3;

    private Boolean ext_activites;

    private Byte state;

    private String product_title;

    private Byte start_state;

    private Integer issue_number;

    private Integer remain_number;

    private Date start_time;

    private String detail;
}
