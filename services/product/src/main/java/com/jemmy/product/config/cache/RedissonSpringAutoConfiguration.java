package com.jemmy.product.config.cache;

import com.jemmy.product.redisson.Customizer;
import com.jemmy.product.redisson.DistributedLocker;
import com.jemmy.product.redisson.RedissonDistributedLocker;
import com.jemmy.product.redisson.RedissonSpringProperties;
import com.jemmy.product.util.RedissLockUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.redisson.spring.transaction.RedissonTransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheAspectSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

/**
 * Redisson Spring 自动配置
 */
@Configuration
@ConditionalOnClass(Redisson.class)
@ConditionalOnBean(RedissonClient.class)
@AutoConfigureAfter({CacheAutoConfiguration.class, TransactionAutoConfiguration.class})
@EnableConfigurationProperties(RedissonSpringProperties.class)
@EnableCaching
public class RedissonSpringAutoConfiguration  extends CachingConfigurerSupport {
    private RedissonSpringProperties redissonSpringProperties;
    private List<Customizer<RedissonSpringCacheManager>> redissonSpringCacheManagerCustomizers;

    private static final Logger logger=LoggerFactory.getLogger(RedissonSpringAutoConfiguration.class);

    public RedissonSpringAutoConfiguration(RedissonSpringProperties redissonSpringProperties,
                                           ObjectProvider<List<Customizer<RedissonSpringCacheManager>>> customizersProvider) {
        this.redissonSpringProperties = redissonSpringProperties;
        this.redissonSpringCacheManagerCustomizers = customizersProvider.getIfAvailable();
        this.redissonSpringCacheManagerCustomizers = redissonSpringCacheManagerCustomizers != null
                ? redissonSpringCacheManagerCustomizers : emptyList();
    }

    /**
     * 声明 RedissonSpringCacheManager
     *
     * 由于 #{@link CacheAutoConfiguration} 的加载顺序在本类之前，并且其默认会注册一个 #{@link org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration}，
     * 所以这里将 beanName 设置为 'cacheManager'，目的是为了覆盖掉默认的 cacheManager（Spring 有一种机制来保证 bean 的优先级，详情请查看
     * #{@link DefaultListableBeanFactory#registerBeanDefinition(String, BeanDefinition)}）
     *
     * 为什么不先加载本类呢？因为 CacheAutoConfiguration 中有一些功能是我们需要的，如果先加载本类，那么 RedissonSpringCacheManager注册成功后，
     * CacheAutoConfiguration 将不会加载，因为其加载条件是不存在 CacheManager
     *
     * @param redisson redisson 客户端
     * @return RedissonSpringCacheManager cacheManager
     */
    @Bean
    @ConditionalOnClass(CacheManager.class)
    @ConditionalOnBean(CacheAspectSupport.class)
    @ConditionalOnMissingBean(RedissonSpringCacheManager.class)
    @ConditionalOnProperty(prefix = "spring.redisson.cache-manager", name = "enabled", havingValue = "true", matchIfMissing = true)
    public RedissonSpringCacheManager cacheManager(RedissonClient redisson) {
        RedissonSpringProperties.RedissonCacheManagerProperties redissonCacheManagerProperties = redissonSpringProperties.getCacheManager();
        // 获取 ConfigMap
        // CacheConfig:
        //   ttl         过期时间，key 写入一定时间后删除，相当于 GuavaCache 的 expireAfterWrite
        //   maxIdleTime 最大空闲时间，key 一定时间内没有被访问后删除，相当于 GuavaCache 的 expireAfterAccess
        //   maxIdleTime 最大数量，达到一定数量后删除一部分 key，基于 LRU 算法
        Map<String, CacheConfig> config = redissonCacheManagerProperties.getConfigs();
        // 创建 CacheManager，ConfigMap 会转换为 Cache
        RedissonSpringCacheManager redissonSpringCacheManager = new RedissonSpringCacheManager(redisson, config);
        // RedissonSpringCacheManager 中的 dynamic 属性默认为 true，即获取不存在的 Cache 时，Redisson 创建一个永不过期的 Cache 以供使用
        // 个人认为这样不合理，会导致滥用缓存，所以 starter 中 dynamic 的默认值为 false，当获取不存在的 Cache 时会抛出异常
        // 当然，你也可以手动开启 dynamic 功能
        if (!redissonCacheManagerProperties.isDynamic()) {
            redissonSpringCacheManager.setCacheNames(redissonCacheManagerProperties.getConfigs().keySet());
        }
        if (redissonCacheManagerProperties.getCodec() != null) {
            redissonSpringCacheManager.setCodec(redissonCacheManagerProperties.getCodec().getInstance());
        }
        if (redissonCacheManagerProperties.getConfigLocation() != null && !redissonCacheManagerProperties.getConfigLocation().isEmpty()) {
            redissonSpringCacheManager.setConfigLocation(redissonCacheManagerProperties.getConfigLocation());
        }
        redissonSpringCacheManager.setAllowNullValues(redissonCacheManagerProperties.isAllowNullValues());
        // 用户自定义配置，拥有最高优先级
        redissonSpringCacheManagerCustomizers.forEach(customizer -> customizer.customize(redissonSpringCacheManager));
        return redissonSpringCacheManager;
    }

    /**
     * 声明 CompositeCacheManager
     *
     * 1. 因为上面已经有 RedissonSpringCacheManager 了，所以这里有 @Primary 修饰
     * 2. 注入 RedissonSpringCacheManager 而不是 CacheManager 是因为该方法只为 RedissonSpringCacheManager 服务 :)
     * 3. 当声明 RedissonSpringCacheManager 的 beanName 为 'cacheManager' 时，@ConditionalOnBean 的条件
     * 'value=RedissonSpringCacheManager.class' 并不能生效，但是别的 beanName 是没问题的，具体原因待查。
     * 另外根据实验得出 spring-boot 1.x 中各条件之间是或的关系，而 spring boot 2.x 中是且的关系。
     *
     * @return CompositeCacheManager cacheManager
     */
    @Bean
    @Primary
    @ConditionalOnBean(name = "cacheManager")
    @ConditionalOnMissingBean(CompositeCacheManager.class)
    @ConditionalOnProperty(prefix = "spring.redisson.cache-manager", name = {"enabled", "fallback-to-no-op-cache"},
            havingValue = "true", matchIfMissing = true)
    public CompositeCacheManager compositeCacheManager(RedissonSpringCacheManager cacheManager) {
        CompositeCacheManager compositeCacheManager = new CompositeCacheManager(cacheManager);
        // 设置 NoOpCacheManager，当获取不存在的 Cache 时不会抛出异常，而是穿透缓存
        compositeCacheManager.setFallbackToNoOpCache(true);
        return compositeCacheManager;
    }

    /**
     * 声明 RedissonTransactionManager
     *
     * 加载顺序放到了 #{@link TransactionAutoConfiguration} 之后，避免影响
     * #{@link DataSourceTransactionManagerAutoConfiguration.DataSourceTransactionManagerConfiguration} 的加载
     *
     * @param redisson redisson 客户端
     * @return RedissonTransactionManager redissonTransactionManager
     */
    @Bean
    @ConditionalOnMissingBean(RedissonTransactionManager.class)
    @ConditionalOnClass(name = "org.springframework.transaction.PlatformTransactionManager")
    @ConditionalOnProperty(prefix = "spring.redisson.transaction", name = "enabled", havingValue = "true")
    public RedissonTransactionManager redissonTransactionManager(RedissonClient redisson) {
        return new RedissonTransactionManager(redisson);
    }

    /**
     * 装配locker类，并将实例注入到RedissLockUtil中
     * @return
     */
    @Bean
    DistributedLocker distributedLocker(RedissonClient redissonClient) {
        RedissonDistributedLocker locker = new RedissonDistributedLocker();
        locker.setRedissonClient(redissonClient);
        RedissLockUtil.setLocker(locker);
        return locker;
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                logger.error("redis异常：key=[{}]",key,e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                logger.error("redis异常：key=[{}]",key,e);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key)    {
                logger.error("redis异常：key=[{}]",key,e);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                logger.error("redis异常：",e);
            }
        };
    }
}
