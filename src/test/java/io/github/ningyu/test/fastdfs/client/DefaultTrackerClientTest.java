package io.github.ningyu.test.fastdfs.client;

import io.github.ningyu.fastdfs.client.DefaultTrackerClient;
import io.github.ningyu.fastdfs.client.TrackerClient;
import io.github.ningyu.fastdfs.conn.DefaultCommandExecutor;
import io.github.ningyu.fastdfs.model.GroupState;
import io.github.ningyu.fastdfs.model.StorageNode;
import io.github.ningyu.fastdfs.model.StorageNodeInfo;
import io.github.ningyu.fastdfs.model.StorageState;
import io.github.ningyu.fastdfs.pool.ConnectionPool;
import io.github.ningyu.fastdfs.pool.PooledConnectionFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author ningyu
 * @date 2017年5月19日 下午2:59 <br/>
 */
public class DefaultTrackerClientTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(DefaultTrackerClientTest.class);

    private static ConnectionPool connectionPool;
    private static TrackerClient trackerClient;

    @BeforeTest
    public void init() {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(500, 500);
        GenericKeyedObjectPoolConfig conf = new GenericKeyedObjectPoolConfig();
        conf.setMaxTotal(200);
        conf.setMaxTotalPerKey(200);
        conf.setMaxIdlePerKey(100);
        connectionPool = new ConnectionPool(pooledConnectionFactory, conf);
        Set<String> trackerSet = new HashSet<String>();
        trackerSet.add("192.168.0.48:22122");
        DefaultCommandExecutor commandExecutor = new DefaultCommandExecutor(trackerSet, connectionPool);
        trackerClient = new DefaultTrackerClient(commandExecutor);
    }

    @AfterTest
    public void close() {
        connectionPool.close();
    }

    @Test
    public void getStorageNodeTest() {
        StorageNode storageNode = trackerClient.getStorageNode();
        logger.info("####===== " + storageNode);
    }

    @Test
    public void getStorageNodeTest2() {
        StorageNode storageNode = trackerClient.getStorageNode("group1");
        logger.info("####===== " + storageNode);

        storageNode = trackerClient.getStorageNode("group2");
        logger.info("####===== " + storageNode);
    }

    @Test
    public void getFetchStorageTest() {
        StorageNodeInfo storageNodeInfo = trackerClient.getFetchStorage("group1", "M00/00/00/wKgAMFkdFtWAPRclAAAAF_BgSIY996.txt");
        logger.info("#####===== " + storageNodeInfo);
    }

    @Test
    public void getFetchStorageAndUpdateTest() {
        StorageNodeInfo storageNodeInfo = trackerClient.getFetchStorageAndUpdate("group1", "M00/00/00/wKgAMFkdFtWAPRclAAAAF_BgSIY996.txt");
        logger.info("#####===== " + storageNodeInfo);
    }

    @Test
    public void getGroupStatesTest() {
        List<GroupState> list = trackerClient.getGroupStates();
        for (GroupState groupState : list) {
            logger.info("#####===== " + groupState);
        }
    }

    @Test
    public void getStorageStatesTest() {
        List<StorageState> list = trackerClient.getStorageStates("group1");
        for (StorageState storageState : list) {
            logger.info("#####===== " + storageState);
        }
    }

    @Test
    public void getStorageStateTest() {
        StorageState storageState = trackerClient.getStorageState("group1", "192.168.0.48");
        logger.info("#####===== " + storageState);

        storageState = trackerClient.getStorageState("group1", "192.168.0.48");
        logger.info("#####===== " + storageState);
    }

    @Test
    public void deleteStorageTest() {
        boolean flag = trackerClient.deleteStorage("group1", "192.168.0.48");
        logger.info("#####===== " + flag);
    }
}
