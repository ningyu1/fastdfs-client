package io.github.ningyu.fastdfs.protocol.storage;

import io.github.ningyu.fastdfs.protocol.BaseResponse;
import io.github.ningyu.fastdfs.protocol.storage.request.DeleteFileRequest;

/**
 * 删除文件爱你命令
 * @author ningyu
 * @date 2017年5月17日 下午4:47:03
 */
public class DeleteFileCommand extends StorageCommand<Void> {
    /**
     * 文件删除命令
     *
     * @param groupName 组名
     * @param path      文件路径
     */
    public DeleteFileCommand(String groupName, String path) {
        super();
        this.request = new DeleteFileRequest(groupName, path);
        // 输出响应
        this.response = new BaseResponse<Void>() {
        };
    }
}
