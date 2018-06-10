package io.github.ningyu.fastdfs.protocol.tracker;

import io.github.ningyu.fastdfs.protocol.BaseResponse;
import io.github.ningyu.fastdfs.protocol.tracker.request.DeleteStorageRequest;

/**
 * 删除存储服务器
 * @author ningyu
 * @date 2017年5月17日 下午3:43 <br/>
 */
public class DeleteStorageCommand extends TrackerCommand<Void> {

    public DeleteStorageCommand(String groupName, String storageIpAddr) {
        super.request = new DeleteStorageRequest(groupName, storageIpAddr);
        super.response = new BaseResponse<Void>() {
        };
    }
}
