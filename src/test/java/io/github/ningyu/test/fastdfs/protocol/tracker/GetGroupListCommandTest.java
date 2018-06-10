package io.github.ningyu.test.fastdfs.protocol.tracker;

import io.github.ningyu.fastdfs.conn.Connection;
import io.github.ningyu.fastdfs.model.GroupState;
import io.github.ningyu.fastdfs.protocol.tracker.GetGroupListCommand;
import io.github.ningyu.test.fastdfs.testbase.GetTrackerConnection;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * @author ningyu
 * @date 2017年5月18日 下午3:13 <br/>
 */
public class GetGroupListCommandTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(GetGroupListCommandTest.class);

    @Test
    public void test01() {
        Connection connection = GetTrackerConnection.getDefaultConnection();
        try {
            GetGroupListCommand command = new GetGroupListCommand();
            List<GroupState> list = command.execute(connection);
            for (GroupState groupState : list) {
                logger.debug(groupState.toString());
            }
        } finally {
            connection.close();
        }
    }
}
