package io.github.ningyu.test.fastdfs.pool;

import io.github.ningyu.fastdfs.conn.DefaultCommandExecutor;
import io.github.ningyu.fastdfs.model.StorageNode;
import io.github.ningyu.fastdfs.pool.ConnectionPool;
import io.github.ningyu.fastdfs.pool.PooledConnectionFactory;
import io.github.ningyu.fastdfs.protocol.tracker.GetStorageNodeCommand;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * @author ningyu
 * @date 2017年5月19日 下午3:45 <br/>
 */
public class ConnectionManagerTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManagerTest.class);

    @Test
    public void test() {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(500, 500);

        GenericKeyedObjectPoolConfig conf = new GenericKeyedObjectPoolConfig();
        conf.setMaxTotal(20);
        ConnectionPool connectionPool = new ConnectionPool(pooledConnectionFactory, conf);

        Set<String> trackerSet = new HashSet<String>();
        trackerSet.add("192.168.0.48:22122");

        DefaultCommandExecutor connectionManager = new DefaultCommandExecutor(trackerSet, connectionPool);

        connectionManager.dumpPoolInfo();

        GetStorageNodeCommand command = new GetStorageNodeCommand();
        StorageNode storageNode = connectionManager.execute(command);
        logger.info(storageNode.toString());

        connectionManager.dumpPoolInfo();

        connectionPool.close();
    }

    @Test
    public void test01() {
        InetSocketAddress address = new InetSocketAddress("192.168.0.48", 125);
        logger.info(address.getAddress() + ":" + address.getPort());
    }

    @Test
    public void test03() throws InterruptedException {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(5000, 5000);

        GenericKeyedObjectPoolConfig conf = new GenericKeyedObjectPoolConfig();
        conf.setMaxTotal(200);
        conf.setMaxTotalPerKey(200);
        conf.setMaxIdlePerKey(100);
        ConnectionPool connectionPool = new ConnectionPool(pooledConnectionFactory, conf);

        Set<String> trackerSet = new HashSet<String>();
        trackerSet.add("192.168.0.48:22122");

        DefaultCommandExecutor connectionManager = new DefaultCommandExecutor(trackerSet, connectionPool);

        for (int i = 0; i <= 50; i++) {
            Thread thread = new PoolTest(connectionManager);
            thread.start();
        }

        for (int i = 0; i <= 2; i++) {
            connectionManager.dumpPoolInfo();
            Thread.sleep(1000 * 2);
        }

        connectionPool.close();
    }

    /**
     * 多线程测试
     */
    private class PoolTest extends Thread {
        private DefaultCommandExecutor connectionManager;

        PoolTest(DefaultCommandExecutor connectionManager) {
            this.connectionManager = connectionManager;
        }

        @Override
        public void run() {
            GetStorageNodeCommand command = new GetStorageNodeCommand();
            StorageNode storageNode = connectionManager.execute(command);
            logger.info("################ " + storageNode.toString());
            connectionManager.dumpPoolInfo();
        }
    }
}
