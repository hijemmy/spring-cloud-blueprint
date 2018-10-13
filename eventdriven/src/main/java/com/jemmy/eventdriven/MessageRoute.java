package com.jemmy.eventdriven;

import lombok.Value;

/**
 * @author Jemmy
 */
@Value
public class MessageRoute {
    private String exchange;

    private String routeKey;
}
