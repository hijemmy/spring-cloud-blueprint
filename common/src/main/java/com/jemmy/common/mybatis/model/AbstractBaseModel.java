package com.jemmy.common.mybatis.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.jemmy.common.web.RootJsonView;
import lombok.Data;

import java.util.Date;

@Data
public abstract class AbstractBaseModel {
    public interface BaseTimeJsonView extends RootJsonView {}
    @JsonView(BaseTimeJsonView.class)
    private Date createTime;
    @JsonView(BaseTimeJsonView.class)
    private Date updateTime;
    private Date deleteTime;
}
