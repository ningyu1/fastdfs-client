package io.github.ningyu.test.fastdfs.protocol.tracker;

import io.github.ningyu.fastdfs.conn.Connection;
import io.github.ningyu.fastdfs.protocol.tracker.DeleteStorageCommand;
import io.github.ningyu.test.fastdfs.testbase.GetTrackerConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * @author ningyu
 * @date 2017年5月18日 下午3:46 <br/>
 */
public class DeleteStorageCommandTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(GetGroupListCommandTest.class);

//    @Test
    public void test01() {
        Connection connection = GetTrackerConnection.getDefaultConnection();
        try {
            //delete storage 在只有一个storage的时候应该是不允许删除的，因此报错
            DeleteStorageCommand command = new DeleteStorageCommand("group1", "192.168.0.48");
            command.execute(connection);
        } finally {
            connection.close();
        }
    }
}
