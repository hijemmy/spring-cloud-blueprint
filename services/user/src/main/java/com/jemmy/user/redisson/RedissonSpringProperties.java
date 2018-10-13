package com.jemmy.user.redisson;

import org.redisson.spring.cache.CacheConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Redisson Spring 自动配置属性
 */
@ConfigurationProperties(prefix = "spring.redisson")
public class RedissonSpringProperties {
    /** Redisson CacheManager 配置 */
    @NestedConfigurationProperty
    private RedissonCacheManagerProperties cacheManager = new RedissonCacheManagerProperties();

    /** Redisson TransactionManager 配置 */
    @NestedConfigurationProperty
    private RedissonTransactionManagerProperties transaction = new RedissonTransactionManagerProperties();


    public static class RedissonCacheManagerProperties {
        /** 是否开启 RedissonSpringCacheManager，默认值：true */
        private boolean enabled = true;
        /** 是否缓存 null 值，默认值：true */
        private boolean allowNullValues = true;
        /** 序列化类型 */
        private CodecType codec;
        /** RedissonCache 配置 */
        private Map<String, CacheConfig> configs = new HashMap<>();
        /** 是否开启动态缓存，默认值：false */
        private boolean dynamic = false;
        /** 缓存配置路径 */
        private String configLocation;
        /** 是否回滚到 NoOpCacheManager，默认值：true */
        private boolean fallbackToNoOpCache = true;

        public RedissonCacheManagerProperties() {
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isAllowNullValues() {
            return allowNullValues;
        }

        public void setAllowNullValues(boolean allowNullValues) {
            this.allowNullValues = allowNullValues;
        }

        public CodecType getCodec() {
            return codec;
        }

        public void setCodec(CodecType codec) {
            this.codec = codec;
        }

        public Map<String, CacheConfig> getConfigs() {
            return configs;
        }

        public void setConfigs(Map<String, CacheConfig> configs) {
            this.configs = configs;
        }

        public boolean isDynamic() {
            return dynamic;
        }

        public void setDynamic(boolean dynamic) {
            this.dynamic = dynamic;
        }

        public String getConfigLocation() {
            return configLocation;
        }

        public void setConfigLocation(String configLocation) {
            this.configLocation = configLocation;
        }

        public boolean isFallbackToNoOpCache() {
            return fallbackToNoOpCache;
        }

        public void setFallbackToNoOpCache(boolean fallbackToNoOpCache) {
            this.fallbackToNoOpCache = fallbackToNoOpCache;
        }
    }

    public static class RedissonTransactionManagerProperties {
        /** 是否开启 RedissonTransactionManager，默认值：false */
        private boolean enabled = false;

        public RedissonTransactionManagerProperties() {
        }

        public boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    public RedissonSpringProperties() {
    }

    public RedissonCacheManagerProperties getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(RedissonCacheManagerProperties cacheManager) {
        this.cacheManager = cacheManager;
    }

    public RedissonTransactionManagerProperties getTransaction() {
        return transaction;
    }

    public void setTransaction(RedissonTransactionManagerProperties transaction) {
        this.transaction = transaction;
    }
}
