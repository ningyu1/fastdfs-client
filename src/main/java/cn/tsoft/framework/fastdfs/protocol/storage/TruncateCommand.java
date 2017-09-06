package cn.tsoft.framework.fastdfs.protocol.storage;

import cn.tsoft.framework.fastdfs.protocol.BaseResponse;
import cn.tsoft.framework.fastdfs.protocol.storage.request.TruncateRequest;

/**
 * 文件Truncate命令
 * @author ningyu
 * @date 2017年5月17日 下午6:53 <br/>
 */
public class TruncateCommand extends StorageCommand<Void> {

    /**
     * 文件Truncate命令
     *
     * @param path     文件路径
     * @param fileSize 文件大小
     */
    public TruncateCommand(String path, long fileSize) {
        super();
        this.request = new TruncateRequest(path, fileSize);
        // 输出响应
        this.response = new BaseResponse<Void>() {
        };
    }
}
