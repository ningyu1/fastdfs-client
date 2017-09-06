package cn.tsoft.framework.fastdfs.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.tsoft.framework.fastdfs.conn.CommandExecutor;
import cn.tsoft.framework.fastdfs.exception.FastDfsException;
import cn.tsoft.framework.fastdfs.model.GroupState;
import cn.tsoft.framework.fastdfs.model.StorageNode;
import cn.tsoft.framework.fastdfs.model.StorageNodeInfo;
import cn.tsoft.framework.fastdfs.model.StorageState;
import cn.tsoft.framework.fastdfs.protocol.tracker.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 目录服务(Tracker)客户端接口 默认实现<br/>
 * <b>注意: 当前类最好使用单例，一个应用只需要一个实例</b>
 * @author ningyu
 * @date 2017年5月17日 下午4:19:06
 */
public class DefaultTrackerClient implements TrackerClient {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(DefaultTrackerClient.class);

    private CommandExecutor commandExecutor;

    public DefaultTrackerClient(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public StorageNode getStorageNode() {
        GetStorageNodeCommand command = new GetStorageNodeCommand();
        return commandExecutor.execute(command);
    }

    @Override
    public StorageNode getStorageNode(String groupName) {
        StorageNode storageNode = null;
        GetStorageNodeCommand command = new GetStorageNodeCommand(groupName);
        try {
            storageNode = commandExecutor.execute(command);
        } catch (FastDfsException e) {
            logger.error("存储节点不存在 groupName=[" + groupName + "]", e);
        }
        return storageNode;
    }

    @Override
    public StorageNodeInfo getFetchStorage(String groupName, String filename) {
        GetFetchStorageCommand command = new GetFetchStorageCommand(groupName, filename, false);
        return commandExecutor.execute(command);
    }

    @Override
    public StorageNodeInfo getFetchStorageAndUpdate(String groupName, String filename) {
        GetFetchStorageCommand command = new GetFetchStorageCommand(groupName, filename, true);
        return commandExecutor.execute(command);
    }

    @Override
    public List<GroupState> getGroupStates() {
        GetGroupListCommand command = new GetGroupListCommand();
        List<GroupState> result = commandExecutor.execute(command);
        return result != null ? result : new ArrayList<GroupState>();
    }

    @Override
    public List<StorageState> getStorageStates(String groupName) {
        GetStorageListCommand command = new GetStorageListCommand(groupName);
        List<StorageState> result = commandExecutor.execute(command);
        return result != null ? result : new ArrayList<StorageState>();
    }

    @Override
    public StorageState getStorageState(String groupName, String storageIp) {
        GetStorageListCommand command = new GetStorageListCommand(groupName, storageIp);
        List<StorageState> result = commandExecutor.execute(command);
        if (result != null && result.size() > 1) {
            logger.warn("应该返回一条数据, 但是现在返回了{}条, 只取第一条", result.size());
        }
        return (result != null && result.size() >= 1) ? result.get(0) : null;
    }

    @Override
    public boolean deleteStorage(String groupName, String storageIp) {
        DeleteStorageCommand command = new DeleteStorageCommand(groupName, storageIp);
        try {
            commandExecutor.execute(command);
        } catch (Throwable e) {
            logger.error("踢出存储服务器失败, groupName=[" + groupName + "], storageIp=[" + storageIp + "]", e);
            return false;
        }
        return true;
    }

    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    public void setCommandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }
}
