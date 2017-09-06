package cn.tsoft.framework.fastdfs.protocol.tracker;

import cn.tsoft.framework.fastdfs.model.StorageNodeInfo;
import cn.tsoft.framework.fastdfs.protocol.BaseResponse;
import cn.tsoft.framework.fastdfs.protocol.tracker.request.GetFetchStorageRequest;

/**
 * 获取文件源存储服务器
 * @author ningyu
 * @date 2017年5月17日 下午3:30 <br/>
 */
public class GetFetchStorageCommand extends TrackerCommand<StorageNodeInfo> {

    /**
     * 获取文件源服务器
     *
     * @param groupName 组名称
     * @param path      路径
     * @param toUpdate  toUpdate
     */
    public GetFetchStorageCommand(String groupName, String path, boolean toUpdate) {
        super.request = new GetFetchStorageRequest(groupName, path, toUpdate);
        super.response = new BaseResponse<StorageNodeInfo>() {
        };
    }
}
