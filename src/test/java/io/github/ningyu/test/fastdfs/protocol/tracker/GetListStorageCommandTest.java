package io.github.ningyu.test.fastdfs.protocol.tracker;

import io.github.ningyu.fastdfs.conn.Connection;
import io.github.ningyu.fastdfs.model.StorageState;
import io.github.ningyu.fastdfs.protocol.tracker.GetStorageListCommand;
import io.github.ningyu.test.fastdfs.testbase.GetTrackerConnection;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

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
