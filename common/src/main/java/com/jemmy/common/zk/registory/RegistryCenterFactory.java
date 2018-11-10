package com.jemmy.common.zk.registory;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.jemmy.common.config.properties.AliyunProperties;
import com.jemmy.common.config.properties.ApplicationProperties;
import com.jemmy.common.config.properties.ZookeeperProperties;
import com.jemmy.common.zk.generator.IncrementIdGenerator;
import com.jemmy.common.zk.registory.base.CoordinatorRegistryCenter;
import com.jemmy.common.zk.registory.base.RegisterDto;
import com.jemmy.common.zk.registory.zookeeper.ZookeeperRegistryCenter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册中心工厂.
 *
 * @author zhangliang
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegistryCenterFactory {

	private static final ConcurrentHashMap<HashCode, CoordinatorRegistryCenter> REG_CENTER_REGISTRY = new ConcurrentHashMap<>();

	/**
	 * 创建注册中心.
	 *
	 * @param zookeeperProperties the zookeeper properties
	 *
	 * @return 注册中心对象 coordinator registry center
	 */
	public static CoordinatorRegistryCenter createCoordinatorRegistryCenter(ZookeeperProperties zookeeperProperties) {
		Hasher hasher = Hashing.md5().newHasher().putString(zookeeperProperties.getZkAddressList(), Charsets.UTF_8);
		HashCode hashCode = hasher.hash();
		CoordinatorRegistryCenter result = REG_CENTER_REGISTRY.get(hashCode);
		if (null != result) {
			return result;
		}
		result = new ZookeeperRegistryCenter(zookeeperProperties);
		result.init();
		REG_CENTER_REGISTRY.put(hashCode, result);
		return result;
	}

	/**
	 * Startup.
	 *
	 * @param paascloudProperties the paascloud properties
	 * @param host                the host
	 * @param app                 the app
	 */
	public static void startup(ApplicationProperties paascloudProperties, String host, String app) {
		CoordinatorRegistryCenter coordinatorRegistryCenter = createCoordinatorRegistryCenter(paascloudProperties.getZk());
		RegisterDto dto = new RegisterDto(app, host, coordinatorRegistryCenter);
		Long serviceId = new IncrementIdGenerator(dto).nextId();
		IncrementIdGenerator.setServiceId(serviceId);
		registerMq(paascloudProperties, host, app);
	}

	private static void registerMq(ApplicationProperties paascloudProperties, String host, String app) {
		CoordinatorRegistryCenter coordinatorRegistryCenter = createCoordinatorRegistryCenter(paascloudProperties.getZk());
		AliyunProperties.RocketMqProperties rocketMq = paascloudProperties.getAliyun().getRocketMq();
		String consumerGroup = rocketMq.isReliableMessageConsumer() ? rocketMq.getConsumerGroup() : null;
		String namesrvAddr = rocketMq.getNamesrvAddr();
		String producerGroup = rocketMq.isReliableMessageProducer() ? rocketMq.getProducerGroup() : null;
		coordinatorRegistryCenter.registerMq(app, host, producerGroup, consumerGroup, namesrvAddr);
	}
}
