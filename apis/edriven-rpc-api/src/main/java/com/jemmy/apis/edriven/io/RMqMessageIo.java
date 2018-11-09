package com.jemmy.apis.edriven.io;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jemmy
 */
@Data
public class RMqMessageIo implements Serializable {
    /**
     * 生产者组
     */
    private String producerGroup;

    /**
     * 业务单据号
     */
    private String refNo;

    /**
     * 消息key
     */
    private String messageKey;

    /**
     * topic
     */
    private String messageTopic;

    /**
     * tag
     */
    private String messageTag;

    /**
     * 消息类型
     */
    private Integer messageType;

    /**
     * 消息内容
     */
    private String messageBody;

    /**
     * 延时消息的延时级别
     */
    private int delayLevel;

    /**
     * 顺序类型, 0有序 1无序
     */
    private int orderType;
}
