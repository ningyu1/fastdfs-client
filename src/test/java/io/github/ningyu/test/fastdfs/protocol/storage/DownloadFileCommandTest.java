package io.github.ningyu.test.fastdfs.protocol.storage;

import io.github.ningyu.fastdfs.conn.Connection;
import io.github.ningyu.fastdfs.protocol.storage.DownloadFileCommand;
import io.github.ningyu.fastdfs.protocol.storage.callback.DownloadFileWriter;
import io.github.ningyu.test.fastdfs.protocol.tracker.GetGroupListCommandTest;
import io.github.ningyu.test.fastdfs.testbase.GetStorageConnection;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * @author ningyu
 * @date 2017年5月17日 下午4:15 <br/>
 */
public class DownloadFileCommandTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(GetGroupListCommandTest.class);

    @Test
    public void test01() {
        Connection connection = GetStorageConnection.getDefaultConnection();
        try {
            String path = ClassLoader.getSystemResource("123456.txt").getPath();
            DownloadFileWriter callback = new DownloadFileWriter(path.replaceAll("123456.txt", "123456downlaod2.txt"));
            DownloadFileCommand<String> command = new DownloadFileCommand<String>("group1", "M00/00/00/wKgAMFkdFtWAPRclAAAAF_BgSIY996.txt", callback);
            String fileName = command.execute(connection);
            logger.info("#####=====DownloadFileCommandTest====" + fileName);
        } finally {
            connection.close();
        }
    }

    @Test
    public void test03() {
        List<Connection> connectionList = new ArrayList<Connection>();
        for (int i = 0; i < 1000; i++) {
            try {
                Connection connection = GetStorageConnection.getDefaultConnection();
                connectionList.add(connection);
                logger.info("新建连接数:" + i);
            } catch (Throwable e) {
                logger.info("连接异常");
                break;
            }
        }

        for (Connection connection : connectionList) {
            connection.close();
        }
    }
}
