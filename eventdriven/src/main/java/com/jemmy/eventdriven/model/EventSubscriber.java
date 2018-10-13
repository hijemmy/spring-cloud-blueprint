package com.jemmy.eventdriven.model;

import com.jemmy.common.event.EventBusinessType;
import com.jemmy.common.model.BaseModel;
import com.jemmy.eventdriven.model.type.EventStatus;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author Jemmy
 */
@Data
public class EventSubscriber extends BaseModel {

    private Long id;
    @NotNull
    @NotBlank
    private EventBusinessType businessType;

    @NotNull
    private EventStatus eventStatus;

    @NotNull
    private String payload;

    @NotNull
    private Integer lockVersion;

    @NotNull
    @NotBlank
    private String guid;

}
