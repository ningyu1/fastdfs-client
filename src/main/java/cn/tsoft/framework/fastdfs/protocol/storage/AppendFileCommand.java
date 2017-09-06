package cn.tsoft.framework.fastdfs.protocol.storage;

import cn.tsoft.framework.fastdfs.protocol.BaseResponse;
import cn.tsoft.framework.fastdfs.protocol.storage.request.AppendFileRequest;

import java.io.InputStream;

/**
 * 添加文件命令
 * @author ningyu
 * @date 2017年5月17日 下午4:46:54
 */
public class AppendFileCommand extends StorageCommand<Void> {

    public AppendFileCommand(InputStream inputStream, long fileSize, String path) {
        this.request = new AppendFileRequest(inputStream, fileSize, path);
        // 输出响应
        this.response = new BaseResponse<Void>() {
        };
    }
}
