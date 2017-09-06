package cn.tsoft.framework.fastdfs.protocol.tracker;

import cn.tsoft.framework.fastdfs.model.StorageNode;
import cn.tsoft.framework.fastdfs.protocol.BaseResponse;
import cn.tsoft.framework.fastdfs.protocol.tracker.request.GetStorageNodeByGroupNameRequest;
import cn.tsoft.framework.fastdfs.protocol.tracker.request.GetStorageNodeRequest;

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
