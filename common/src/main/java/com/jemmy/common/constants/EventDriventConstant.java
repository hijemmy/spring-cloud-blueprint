package com.jemmy.common.constants;

public class EventDriventConstant {

    private EventDriventConstant(){}
    private static final String DLQ_SUFFIX=".dlq";
    private static final String ROUTING_KEY_SUFFIX=".key";

    /**
     * order相关队列
     */
    public static final String EVENT_DRIVEN_ORDER_EXCHANGE = "order";
    public static final String EVENT_DRIVEN_ORDER_QUEUE = "order";
    public static final String EVENT_DRIVEN_ORDER_ROUTING_KEY = EVENT_DRIVEN_ORDER_QUEUE+ROUTING_KEY_SUFFIX;
    public static final String EVENT_DRIVEN_ORDER_QUEUE_DLQ=EVENT_DRIVEN_ORDER_QUEUE+DLQ_SUFFIX;
    public static final String EVENT_DRIVEN_ORDER_QUEUE_DLQ_ROUTING_KEY=EVENT_DRIVEN_ORDER_QUEUE_DLQ+ROUTING_KEY_SUFFIX;

    /**
     * product相关队列
     */
    public static final String EVENT_DRIVEN_PRODUCT_EXCHANGE = "product";
    public static final String EVENT_DRIVEN_PRODUCT_QUEUE = "product";
    public static final String EVENT_DRIVEN_PRODUCT_ROUTING_KEY = EVENT_DRIVEN_PRODUCT_QUEUE+ROUTING_KEY_SUFFIX;
    public static final String EVENT_DRIVEN_PRODUCT_QUEUE_DLQ=EVENT_DRIVEN_PRODUCT_QUEUE+DLQ_SUFFIX;
    public static final String EVENT_DRIVEN_PRODUCT_QUEUE_DLQ_ROUTING_KEY=EVENT_DRIVEN_PRODUCT_QUEUE_DLQ+ROUTING_KEY_SUFFIX;

}
