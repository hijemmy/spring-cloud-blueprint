package com.jemmy.common.model.event;

import com.jemmy.common.event.EventBusinessType;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Jemmy
 */
@Data
public class Event implements Serializable {

    @NotNull
    @NotBlank
    private EventBusinessType businessType;

    @NotNull
    @NotBlank
    //@JsonRawValue
    private String payload;

    @NotNull
    @NotBlank
    private String guid;

}
