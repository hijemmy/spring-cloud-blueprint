package com.jemmy.common.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.jemmy.common.web.RootJsonView;
import lombok.Data;

import java.util.Date;

@Data
public class BaseModel {
    public interface BaseTimeJsonView extends RootJsonView {}
    @JsonView(BaseTimeJsonView.class)
    private Date createTime;
    @JsonView(BaseTimeJsonView.class)
    private Date updateTime;
    private Date deleteTime;
}
