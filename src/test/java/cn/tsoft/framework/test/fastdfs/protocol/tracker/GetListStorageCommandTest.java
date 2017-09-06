package cn.tsoft.framework.test.fastdfs.protocol.tracker;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import cn.tsoft.framework.fastdfs.conn.Connection;
import cn.tsoft.framework.fastdfs.model.StorageState;
import cn.tsoft.framework.fastdfs.protocol.tracker.GetStorageListCommand;
import cn.tsoft.framework.test.fastdfs.testbase.GetTrackerConnection;

/**
 * @author ningyu
 * @date 2017年5月18日 中午12:51 <br/>
 */
public class GetListStorageCommandTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(GetListStorageCommandTest.class);

    @Test
    public void test01() {
        Connection connection = GetTrackerConnection.getDefaultConnection();
        try {
            GetStorageListCommand command = new GetStorageListCommand("group1");
            List<StorageState> storageStates = command.execute(connection);
            for (StorageState storageState : storageStates) {
                logger.debug(storageState.toString());
            }
        } finally {
            connection.close();
        }
    }
}
