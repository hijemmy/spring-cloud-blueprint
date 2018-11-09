package com.jemmy.elastic.lite.autoconfigure;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.jemmy.elastic.lite.ZookeeperRegistryProperties;
import com.jemmy.elastic.lite.annotation.ElasticJobConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册中心配置
 *
 * @author jemmy
 */
@Configuration
@ConditionalOnClass(ElasticJob.class)
@ConditionalOnBean(annotation = ElasticJobConfig.class)
@EnableConfigurationProperties(ZookeeperRegistryProperties.class)
public class RegistryCenterConfiguration {

	private final ZookeeperRegistryProperties regCenterProperties;


	@Autowired
	public RegistryCenterConfiguration(ZookeeperRegistryProperties regCenterProperties) {
		this.regCenterProperties = regCenterProperties;
	}

	/**
	 * zookeeper注册中心Bean
	 *
	 * @return the zookeeper registry center
	 */
	@Bean(initMethod = "init")
	@ConditionalOnMissingBean
	public ZookeeperRegistryCenter regCenter() {
		ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(regCenterProperties.getZkAddressList(), regCenterProperties.getNamespace());
		zookeeperConfiguration.setBaseSleepTimeMilliseconds(regCenterProperties.getBaseSleepTimeMilliseconds());
		zookeeperConfiguration.setConnectionTimeoutMilliseconds(regCenterProperties.getConnectionTimeoutMilliseconds());
		zookeeperConfiguration.setMaxSleepTimeMilliseconds(regCenterProperties.getMaxSleepTimeMilliseconds());
		zookeeperConfiguration.setSessionTimeoutMilliseconds(regCenterProperties.getSessionTimeoutMilliseconds());
		zookeeperConfiguration.setMaxRetries(regCenterProperties.getMaxRetries());
		zookeeperConfiguration.setDigest(regCenterProperties.getDigest());
		return new ZookeeperRegistryCenter(zookeeperConfiguration);
	}

}
