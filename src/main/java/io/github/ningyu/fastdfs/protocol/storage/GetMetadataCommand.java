package io.github.ningyu.fastdfs.protocol.storage;

import io.github.ningyu.fastdfs.model.MateData;
import io.github.ningyu.fastdfs.protocol.storage.request.GetMetadataRequest;
import io.github.ningyu.fastdfs.protocol.storage.response.GetMetadataResponse;

import java.util.Set;

/**
 * @author ningyu
 * @date 2017年5月17日 下午4:47:36
 */
public class GetMetadataCommand extends StorageCommand<Set<MateData>> {

    /**
     * 设置文件标签(元数据)
     *
     * @param groupName 组名
     * @param path      文件路径
     */
    public GetMetadataCommand(String groupName, String path) {
        this.request = new GetMetadataRequest(groupName, path);
        // 输出响应
        this.response = new GetMetadataResponse();
    }
}
