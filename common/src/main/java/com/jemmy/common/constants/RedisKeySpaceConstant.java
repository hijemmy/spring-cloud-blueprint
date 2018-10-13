package com.jemmy.common.constants;

/**
 * Redis键值命名空间常量值
 */
public class RedisKeySpaceConstant {
    private RedisKeySpaceConstant(){}

    public static final String REDIS_NS_SEPARATOR=":";

    /* *短信前缀 */
    public static final String REDIS_KEY_SMS="sms";
    /**
     * 系统配置前缀
     */
    public static final String REDIS_KEY_SETTING="setting";

    /**
     * 阿里短信
     */
    private static final String REDIS_KEY_ALI_SMS=REDIS_KEY_SMS+REDIS_NS_SEPARATOR+"ali";
    /**
     * 验证码短信
     */
    public static final String REDIS_KEY_ALI_SMS_CODE=REDIS_KEY_ALI_SMS+REDIS_NS_SEPARATOR+"code";


    /**
     * 分布式锁
     */
    private static final String LOCK_PREFIX="dislock";
    /**
     * 订单
     */
    private static final String LOCK_ORDER=LOCK_PREFIX+REDIS_NS_SEPARATOR+"order";
    public static final String LOCK_ORDER_NUMBER=LOCK_ORDER+REDIS_NS_SEPARATOR+"number";
}
