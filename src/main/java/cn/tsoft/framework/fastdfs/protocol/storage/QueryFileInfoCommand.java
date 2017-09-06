package cn.tsoft.framework.fastdfs.protocol.storage;

import cn.tsoft.framework.fastdfs.model.FileInfo;
import cn.tsoft.framework.fastdfs.protocol.BaseResponse;
import cn.tsoft.framework.fastdfs.protocol.storage.request.QueryFileInfoRequest;

/**
 * @author ningyu
 * @date 2017年5月17日 下午6:34 <br/>
 */
public class QueryFileInfoCommand extends StorageCommand<FileInfo> {

    /**
     * 文件上传命令
     *
     * @param groupName 组名称
     * @param path      文件路径
     */
    public QueryFileInfoCommand(String groupName, String path) {
        super();
        this.request = new QueryFileInfoRequest(groupName, path);
        // 输出响应
        this.response = new BaseResponse<FileInfo>() {
        };
    }
}