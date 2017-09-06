package cn.tsoft.framework.test.fastdfs.protocol.tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import cn.tsoft.framework.fastdfs.conn.Connection;
import cn.tsoft.framework.fastdfs.model.StorageNode;
import cn.tsoft.framework.fastdfs.protocol.tracker.GetStorageNodeCommand;
import cn.tsoft.framework.test.fastdfs.testbase.GetTrackerConnection;

/**
 * @author ningyu
 * @date 2017年5月18日 下午3:16 <br/>
 */
public class GetStorageNodeCommandTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(GetListStorageCommandTest.class);

    @Test
    public void test01() {
        Connection connection = GetTrackerConnection.getDefaultConnection();
        try {
            GetStorageNodeCommand command = new GetStorageNodeCommand();
            StorageNode storageNode = command.execute(connection);
            logger.info(storageNode.toString());

            command = new GetStorageNodeCommand("group1");
            storageNode = command.execute(connection);
            logger.info(storageNode.toString());
        } finally {
            connection.close();
        }
    }
}
