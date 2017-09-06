package cn.tsoft.framework.test.fastdfs.protocol.tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import cn.tsoft.framework.fastdfs.conn.Connection;
import cn.tsoft.framework.fastdfs.model.StorageNodeInfo;
import cn.tsoft.framework.fastdfs.protocol.tracker.GetFetchStorageCommand;
import cn.tsoft.framework.test.fastdfs.testbase.GetTrackerConnection;

/**
 * @author ningyu
 * @date 2017年5月18日 下午3:37 <br/>
 */
public class GetFetchStorageCommandTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(GetGroupListCommandTest.class);

    @Test
    public void test01() {
        Connection connection = GetTrackerConnection.getDefaultConnection();
        try {
            GetFetchStorageCommand command = new GetFetchStorageCommand("group1", "M00/00/00/wKgAMFkdFtWAPRclAAAAF_BgSIY996.txt", false);
            StorageNodeInfo storageNodeInfo = command.execute(connection);
            logger.info(storageNodeInfo.toString());
        } finally {
            connection.close();
        }
    }
}
