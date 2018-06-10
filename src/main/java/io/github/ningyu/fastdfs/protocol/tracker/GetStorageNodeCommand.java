package io.github.ningyu.fastdfs.protocol.tracker;

import io.github.ningyu.fastdfs.model.StorageNode;
import io.github.ningyu.fastdfs.protocol.BaseResponse;
import io.github.ningyu.fastdfs.protocol.tracker.request.GetStorageNodeByGroupNameRequest;
import io.github.ningyu.fastdfs.protocol.tracker.request.GetStorageNodeRequest;

/**
 * 获取存储节点命令
 * @author ningyu
 * @date 2017年5月17日 下午3:16 <br/>
 */
public class GetStorageNodeCommand extends TrackerCommand<StorageNode> {

    public GetStorageNodeCommand(String groupName) {
        super.request = new GetStorageNodeByGroupNameRequest(groupName);
        super.response = new BaseResponse<StorageNode>() {
        };
    }

    public GetStorageNodeCommand() {
        super.request = new GetStorageNodeRequest();
        super.response = new BaseResponse<StorageNode>() {
        };
    }
}
