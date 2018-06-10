package io.github.ningyu.fastdfs.protocol.tracker;

import io.github.ningyu.fastdfs.model.StorageState;
import io.github.ningyu.fastdfs.protocol.tracker.request.GetListStorageRequest;
import io.github.ningyu.fastdfs.protocol.tracker.response.GetListStorageResponse;

import java.util.List;

/**
 * 获取Storage服务器状态命令
 * @author ningyu
 * @date 2017年5月17日 中午12:41 <br/>
 */
public class GetStorageListCommand extends TrackerCommand<List<StorageState>> {

    public GetStorageListCommand(String groupName, String storageIpAddr) {
        super.request = new GetListStorageRequest(groupName, storageIpAddr);
        super.response = new GetListStorageResponse();
    }

    public GetStorageListCommand(String groupName) {
        super.request = new GetListStorageRequest(groupName);
        super.response = new GetListStorageResponse();
    }
}
