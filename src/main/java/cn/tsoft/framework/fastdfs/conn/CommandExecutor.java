package cn.tsoft.framework.fastdfs.conn;

import cn.tsoft.framework.fastdfs.protocol.storage.StorageCommand;
import cn.tsoft.framework.fastdfs.protocol.tracker.TrackerCommand;

import java.net.InetSocketAddress;

/**
 * FastDFS命令执行器
 * <p>
 * 命令执行器
 * @author ningyu
 * @date 2017年5月17日 下午4:19:30
 */
public interface CommandExecutor {

    /**
     * 在Tracker Server上执行命令
     *
     * @param command Tracker Server命令
     * @param <T>     返回数据类型
     * @return 返回数据
     */
    <T> T execute(TrackerCommand<T> command);

    /**
     * 在Storage Server上执行命令
     *
     * @param address Storage Server地址
     * @param command Storage Server命令
     * @param <T>     返回数据类型
     * @return 返回数据
     */
    <T> T execute(InetSocketAddress address, StorageCommand<T> command);
}
