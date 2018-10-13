package com.jemmy.order.redisson;

import io.netty.channel.EventLoopGroup;
import org.redisson.codec.DefaultReferenceCodecProvider;
import org.redisson.codec.ReferenceCodecProvider;
import org.redisson.config.ReadMode;
import org.redisson.config.SslProvider;
import org.redisson.config.SubscriptionMode;
import org.redisson.config.TransportMode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.net.URI;
import java.util.concurrent.ExecutorService;

/**
 * Redis配置项
 */
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {

    /** 线程池数量，默认值：当前处理核数量*2 */
    private int threads = 0;
    /** Netty 线程池数量，默认值：当前处理核数量*2 */
    private int nettyThreads = 0;
    /** Redis 进行序列化和反序列化的类型，默认值：jackson */
    private CodecType codec = CodecType.JACKSON;
    /** Codec 注册和获取功能的提供者，默认值：DefaultReferenceCodecProvider */
    private ReferenceCodecProvider referenceCodecProvider = new DefaultReferenceCodecProvider();
    /** 单独提供一个线程池实例 */
    private ExecutorService executor;
    /** Redisson 参考功能的配置选项，默认值：true */
    private boolean referenceEnabled = true;
    /** TransportMode，默认值：NIO */
    private TransportMode transportMode = TransportMode.NIO;
    /** 单独指定一个 EventLoopGroup */
    private EventLoopGroup eventLoopGroup;
    /** 锁监视器的超时时间，默认值：30000 ms */
    private long lockWatchdogTimeout = 30 * 1000;
    /** 是否顺序处理或并发处理 PubSub 消息，默认值：true */
    private boolean keepPubSubOrder = true;
    /** Redis 服务端模式，默认值：single */
    private RedissonType type = RedissonType.SINGLE;
    /** 地址解析器，默认值：DnsAddressResolverGroupFactory */
    private AddressResolverGroupFactoryType addressResolverGroupFactory = AddressResolverGroupFactoryType.DEFAULT;

    /** 单节点模式 */
    @NestedConfigurationProperty
    private SingleServerConfig single = new SingleServerConfig();
    /** 集群模式 */
    @NestedConfigurationProperty
    private ClusterServersConfig cluster = new ClusterServersConfig();
    /** 主从模式 */
    @NestedConfigurationProperty
    private MasterSlaveServersConfig masterSlave = new MasterSlaveServersConfig();
    /** 哨兵模式 */
    @NestedConfigurationProperty
    private SentinelServersConfig sentinel = new SentinelServersConfig();
    /** 云托管模式 */
    @NestedConfigurationProperty
    private ReplicatedServersConfig replicated = new ReplicatedServersConfig();

    private static class BaseConfig {
        /** 连接空闲超时时间，默认值：10000 ms */
        private int idleConnectionTimeout = 10000;
        /** PING 操作的超时时间，默认值：1000 ms */
        private int pingTimeout = 1000;
        /** 连接超时时间，默认值：10000 ms */
        private int connectTimeout = 10000;
        /** 命令等待超时时间，，默认值：3000 ms */
        private int timeout = 3000;
        /** 命令失败重试次数，默认值：3 */
        private int retryAttempts = 3;
        /** 命令重试发送时间间隔，默认值：1500 ms */
        private int retryInterval = 1500;
        /** Redis 实例密码，默认值：null */
        private String password = null;
        /** 单个连接最大订阅数量，默认值：5 */
        private int subscriptionsPerConnection = 5;
        /** 客户端名称，默认值：null */
        private String clientName = null;
        /** 启用 SSL 终端识别，默认值：true */
        private boolean sslEnableEndpointIdentification = true;
        /** SSL 实现方式，默认值：jdk */
        private SslProvider sslProvider = SslProvider.JDK;
        /** SSL 信任证书库路径，默认值：null */
        private URI sslTrustStore = null;
        /** SSL 信任证书库密码，默认值：null */
        private String sslTrustStorePassword = null;
        /** SSL 钥匙库路径，默认值：null */
        private URI sslKeystore = null;
        /** SSL 钥匙库密码，默认值：null */
        private String sslKeystorePassword = null;
        /** PING 命令的发送时间间隔，默认值：0 ms */
        private int pingConnectionInterval = 0;
        /** 开启连接的 TCP KeepAlive 特性，默认值：false */
        private boolean keepAlive = false;
        /** 开启连接的 TCP NoDelay 特性，默认值：false */
        private boolean tcpNoDelay = false;

        public BaseConfig() {
        }

        public int getIdleConnectionTimeout() {
            return idleConnectionTimeout;
        }

        public void setIdleConnectionTimeout(int idleConnectionTimeout) {
            this.idleConnectionTimeout = idleConnectionTimeout;
        }

        public int getPingTimeout() {
            return pingTimeout;
        }

        public void setPingTimeout(int pingTimeout) {
            this.pingTimeout = pingTimeout;
        }

        public int getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public int getRetryAttempts() {
            return retryAttempts;
        }

        public void setRetryAttempts(int retryAttempts) {
            this.retryAttempts = retryAttempts;
        }

        public int getRetryInterval() {
            return retryInterval;
        }

        public void setRetryInterval(int retryInterval) {
            this.retryInterval = retryInterval;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getSubscriptionsPerConnection() {
            return subscriptionsPerConnection;
        }

        public void setSubscriptionsPerConnection(int subscriptionsPerConnection) {
            this.subscriptionsPerConnection = subscriptionsPerConnection;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public boolean isSslEnableEndpointIdentification() {
            return sslEnableEndpointIdentification;
        }

        public void setSslEnableEndpointIdentification(boolean sslEnableEndpointIdentification) {
            this.sslEnableEndpointIdentification = sslEnableEndpointIdentification;
        }

        public SslProvider getSslProvider() {
            return sslProvider;
        }

        public void setSslProvider(SslProvider sslProvider) {
            this.sslProvider = sslProvider;
        }

        public URI getSslTrustStore() {
            return sslTrustStore;
        }

        public void setSslTrustStore(URI sslTrustStore) {
            this.sslTrustStore = sslTrustStore;
        }

        public String getSslTrustStorePassword() {
            return sslTrustStorePassword;
        }

        public void setSslTrustStorePassword(String sslTrustStorePassword) {
            this.sslTrustStorePassword = sslTrustStorePassword;
        }

        public URI getSslKeystore() {
            return sslKeystore;
        }

        public void setSslKeystore(URI sslKeystore) {
            this.sslKeystore = sslKeystore;
        }

        public String getSslKeystorePassword() {
            return sslKeystorePassword;
        }

        public void setSslKeystorePassword(String sslKeystorePassword) {
            this.sslKeystorePassword = sslKeystorePassword;
        }

        public int getPingConnectionInterval() {
            return pingConnectionInterval;
        }

        public void setPingConnectionInterval(int pingConnectionInterval) {
            this.pingConnectionInterval = pingConnectionInterval;
        }

        public boolean isKeepAlive() {
            return keepAlive;
        }

        public void setKeepAlive(boolean keepAlive) {
            this.keepAlive = keepAlive;
        }

        public boolean isTcpNoDelay() {
            return tcpNoDelay;
        }

        public void setTcpNoDelay(boolean tcpNoDelay) {
            this.tcpNoDelay = tcpNoDelay;
        }
    }


    private static class BaseMasterSlaveServersConfig extends BaseConfig {
        /** 负载均衡算法，默认值：round_robin */
        private LoadBalancerType loadBalancer = LoadBalancerType.ROUND_ROBIN;
        /** 主节点最小空闲连接数，默认值：32 */
        private int masterConnectionMinimumIdleSize = 32;
        /** 主节点连接池大小，默认值：64 */
        private int masterConnectionPoolSize = 64;
        /** 从节点最小空闲连接数，默认值：32 */
        private int slaveConnectionMinimumIdleSize = 32;
        /** 从节点连接池大小，默认值：64 */
        private int slaveConnectionPoolSize = 64;
        /** 当第一个 Redis 命令执行失败的时间间隔到达该值时，从节点将被排除在可用节点的内部列表中，默认值：60000 ms */
        private int failedSlaveCheckInterval = 60000;
        /** 当节点被排除在可用服务器的内部列表中时，从节点重新连接尝试的间隔，默认值：3000 ms */
        private int failedSlaveReconnectionInterval = 3000;
        /** 读取操作的负载均衡模式，默认值：slave */
        private ReadMode readMode = ReadMode.SLAVE;
        /** 订阅操作的负载均衡模式，默认值：master */
        private SubscriptionMode subscriptionMode = SubscriptionMode.MASTER;
        /** 从节点发布和订阅连接的最小空闲连接数，默认值：1 */
        private int subscriptionConnectionMinimumIdleSize = 1;
        /** 从节点发布和订阅连接池大小，默认值：50 */
        private int subscriptionConnectionPoolSize = 50;
        /** DNS 监测时间间隔，默认值：5000 ms */
        private long dnsMonitoringInterval = 5000;

        public LoadBalancerType getLoadBalancer() {
            return loadBalancer;
        }

        public void setLoadBalancer(LoadBalancerType loadBalancer) {
            this.loadBalancer = loadBalancer;
        }

        public int getMasterConnectionMinimumIdleSize() {
            return masterConnectionMinimumIdleSize;
        }

        public void setMasterConnectionMinimumIdleSize(int masterConnectionMinimumIdleSize) {
            this.masterConnectionMinimumIdleSize = masterConnectionMinimumIdleSize;
        }

        public int getMasterConnectionPoolSize() {
            return masterConnectionPoolSize;
        }

        public void setMasterConnectionPoolSize(int masterConnectionPoolSize) {
            this.masterConnectionPoolSize = masterConnectionPoolSize;
        }

        public int getSlaveConnectionMinimumIdleSize() {
            return slaveConnectionMinimumIdleSize;
        }

        public void setSlaveConnectionMinimumIdleSize(int slaveConnectionMinimumIdleSize) {
            this.slaveConnectionMinimumIdleSize = slaveConnectionMinimumIdleSize;
        }

        public int getSlaveConnectionPoolSize() {
            return slaveConnectionPoolSize;
        }

        public void setSlaveConnectionPoolSize(int slaveConnectionPoolSize) {
            this.slaveConnectionPoolSize = slaveConnectionPoolSize;
        }

        public int getFailedSlaveCheckInterval() {
            return failedSlaveCheckInterval;
        }

        public void setFailedSlaveCheckInterval(int failedSlaveCheckInterval) {
            this.failedSlaveCheckInterval = failedSlaveCheckInterval;
        }

        public int getFailedSlaveReconnectionInterval() {
            return failedSlaveReconnectionInterval;
        }

        public void setFailedSlaveReconnectionInterval(int failedSlaveReconnectionInterval) {
            this.failedSlaveReconnectionInterval = failedSlaveReconnectionInterval;
        }

        public ReadMode getReadMode() {
            return readMode;
        }

        public void setReadMode(ReadMode readMode) {
            this.readMode = readMode;
        }

        public SubscriptionMode getSubscriptionMode() {
            return subscriptionMode;
        }

        public void setSubscriptionMode(SubscriptionMode subscriptionMode) {
            this.subscriptionMode = subscriptionMode;
        }

        public int getSubscriptionConnectionMinimumIdleSize() {
            return subscriptionConnectionMinimumIdleSize;
        }

        public void setSubscriptionConnectionMinimumIdleSize(int subscriptionConnectionMinimumIdleSize) {
            this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
        }

        public int getSubscriptionConnectionPoolSize() {
            return subscriptionConnectionPoolSize;
        }

        public void setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
            this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
        }

        public long getDnsMonitoringInterval() {
            return dnsMonitoringInterval;
        }

        public void setDnsMonitoringInterval(long dnsMonitoringInterval) {
            this.dnsMonitoringInterval = dnsMonitoringInterval;
        }
    }

    public static class SingleServerConfig extends BaseConfig {
        /** 节点地址，格式：redis://host:port */
        private String address = "redis://127.0.0.1:6379";
        /** 数据库编号，默认值：0 */
        private int database = 0;
        /** 最小空闲连接数，默认值：32 */
        private int connectionMinimumIdleSize = 32;
        /** 连接池大小，默认值：64 */
        private int connectionPoolSize = 64;
        /** 发布和订阅连接的最小空闲连接数，默认值：1 */
        private int subscriptionConnectionMinimumIdleSize = 1;
        /** 发布和订阅连接池大小，默认值：50 */
        private int subscriptionConnectionPoolSize = 50;
        /** DNS 监测时间间隔，默认值：5000 ms */
        private long dnsMonitoringInterval = 5000L;

        public SingleServerConfig() {
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getDatabase() {
            return database;
        }

        public void setDatabase(int database) {
            this.database = database;
        }

        public int getConnectionMinimumIdleSize() {
            return connectionMinimumIdleSize;
        }

        public void setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
            this.connectionMinimumIdleSize = connectionMinimumIdleSize;
        }

        public int getConnectionPoolSize() {
            return connectionPoolSize;
        }

        public void setConnectionPoolSize(int connectionPoolSize) {
            this.connectionPoolSize = connectionPoolSize;
        }

        public int getSubscriptionConnectionMinimumIdleSize() {
            return subscriptionConnectionMinimumIdleSize;
        }

        public void setSubscriptionConnectionMinimumIdleSize(int subscriptionConnectionMinimumIdleSize) {
            this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
        }

        public int getSubscriptionConnectionPoolSize() {
            return subscriptionConnectionPoolSize;
        }

        public void setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
            this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
        }

        public long getDnsMonitoringInterval() {
            return dnsMonitoringInterval;
        }

        public void setDnsMonitoringInterval(long dnsMonitoringInterval) {
            this.dnsMonitoringInterval = dnsMonitoringInterval;
        }
    }

    public static class ClusterServersConfig extends BaseMasterSlaveServersConfig {
        /** 集群节点地址，格式：redis://host:port */
        private String[] nodeAddresses;
        /** 集群扫描间隔时间，默认值：1000 ms */
        private int scanInterval = 1000;

        public ClusterServersConfig() {
        }

        public String[] getNodeAddresses() {
            return nodeAddresses;
        }

        public void setNodeAddresses(String[] nodeAddresses) {
            this.nodeAddresses = nodeAddresses;
        }

        public int getScanInterval() {
            return scanInterval;
        }

        public void setScanInterval(int scanInterval) {
            this.scanInterval = scanInterval;
        }
    }

    public static class MasterSlaveServersConfig extends BaseMasterSlaveServersConfig {
        /** 主节点地址，格式：redis://host:port */
        private String masterAddress;
        /** 从节点地址，格式：redis://host:port */
        private String[] slaveAddresses;
        /** 数据库编号，默认值：0 */
        private int database = 0;

        public MasterSlaveServersConfig() {
        }

        public String getMasterAddress() {
            return masterAddress;
        }

        public void setMasterAddress(String masterAddress) {
            this.masterAddress = masterAddress;
        }

        public String[] getSlaveAddresses() {
            return slaveAddresses;
        }

        public void setSlaveAddresses(String[] slaveAddresses) {
            this.slaveAddresses = slaveAddresses;
        }

        public int getDatabase() {
            return database;
        }

        public void setDatabase(int database) {
            this.database = database;
        }
    }


    public static class SentinelServersConfig extends BaseMasterSlaveServersConfig {
        /** 哨兵节点地址，格式：redis://host:port */
        private String[] sentinelAddresses;
        /** 主服务器的名称，默认值：null */
        private String masterName = null;
        /** 哨兵扫描间隔时间，默认值：1000 ms */
        private int scanInterval = 1000;
        /** 数据库编号，默认值：0 */
        private int database = 0;

        public SentinelServersConfig() {
        }

        public String[] getSentinelAddresses() {
            return sentinelAddresses;
        }

        public void setSentinelAddresses(String[] sentinelAddresses) {
            this.sentinelAddresses = sentinelAddresses;
        }

        public String getMasterName() {
            return masterName;
        }

        public void setMasterName(String masterName) {
            this.masterName = masterName;
        }

        public int getScanInterval() {
            return scanInterval;
        }

        public void setScanInterval(int scanInterval) {
            this.scanInterval = scanInterval;
        }

        public int getDatabase() {
            return database;
        }

        public void setDatabase(int database) {
            this.database = database;
        }
    }


    public static class ReplicatedServersConfig extends BaseMasterSlaveServersConfig {
        /** 集群节点地址，格式：redis://host:port */
        private String[] nodeAddresses;
        /** 主节点变化扫描间隔时间，默认值：1000 ms */
        private int scanInterval = 1000;
        /** 数据库编号，默认值：0 */
        private int database = 0;

        public ReplicatedServersConfig() {
        }

        public String[] getNodeAddresses() {
            return nodeAddresses;
        }

        public void setNodeAddresses(String[] nodeAddresses) {
            this.nodeAddresses = nodeAddresses;
        }

        public int getScanInterval() {
            return scanInterval;
        }

        public void setScanInterval(int scanInterval) {
            this.scanInterval = scanInterval;
        }

        public int getDatabase() {
            return database;
        }

        public void setDatabase(int database) {
            this.database = database;
        }
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public int getNettyThreads() {
        return nettyThreads;
    }

    public void setNettyThreads(int nettyThreads) {
        this.nettyThreads = nettyThreads;
    }

    public CodecType getCodec() {
        return codec;
    }

    public void setCodec(CodecType codec) {
        this.codec = codec;
    }

    public ReferenceCodecProvider getReferenceCodecProvider() {
        return referenceCodecProvider;
    }

    public void setReferenceCodecProvider(ReferenceCodecProvider referenceCodecProvider) {
        this.referenceCodecProvider = referenceCodecProvider;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public boolean isReferenceEnabled() {
        return referenceEnabled;
    }

    public void setReferenceEnabled(boolean referenceEnabled) {
        this.referenceEnabled = referenceEnabled;
    }

    public TransportMode getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(TransportMode transportMode) {
        this.transportMode = transportMode;
    }

    public EventLoopGroup getEventLoopGroup() {
        return eventLoopGroup;
    }

    public void setEventLoopGroup(EventLoopGroup eventLoopGroup) {
        this.eventLoopGroup = eventLoopGroup;
    }

    public long getLockWatchdogTimeout() {
        return lockWatchdogTimeout;
    }

    public void setLockWatchdogTimeout(long lockWatchdogTimeout) {
        this.lockWatchdogTimeout = lockWatchdogTimeout;
    }

    public boolean isKeepPubSubOrder() {
        return keepPubSubOrder;
    }

    public void setKeepPubSubOrder(boolean keepPubSubOrder) {
        this.keepPubSubOrder = keepPubSubOrder;
    }

    public RedissonType getType() {
        return type;
    }

    public void setType(RedissonType type) {
        this.type = type;
    }

    public AddressResolverGroupFactoryType getAddressResolverGroupFactory() {
        return addressResolverGroupFactory;
    }

    public void setAddressResolverGroupFactory(AddressResolverGroupFactoryType addressResolverGroupFactory) {
        this.addressResolverGroupFactory = addressResolverGroupFactory;
    }

    public SingleServerConfig getSingle() {
        return single;
    }

    public void setSingle(SingleServerConfig single) {
        this.single = single;
    }

    public ClusterServersConfig getCluster() {
        return cluster;
    }

    public void setCluster(ClusterServersConfig cluster) {
        this.cluster = cluster;
    }

    public MasterSlaveServersConfig getMasterSlave() {
        return masterSlave;
    }

    public void setMasterSlave(MasterSlaveServersConfig masterSlave) {
        this.masterSlave = masterSlave;
    }

    public SentinelServersConfig getSentinel() {
        return sentinel;
    }

    public void setSentinel(SentinelServersConfig sentinel) {
        this.sentinel = sentinel;
    }

    public ReplicatedServersConfig getReplicated() {
        return replicated;
    }

    public void setReplicated(ReplicatedServersConfig replicated) {
        this.replicated = replicated;
    }
}
