package io.github.ningyu.fastdfs.protocol.storage.request;

import io.github.ningyu.fastdfs.constant.CmdConstants;
import io.github.ningyu.fastdfs.constant.OtherConstants;
import io.github.ningyu.fastdfs.mapper.DynamicFieldType;
import io.github.ningyu.fastdfs.mapper.FastDFSColumn;
import io.github.ningyu.fastdfs.protocol.BaseRequest;
import io.github.ningyu.fastdfs.protocol.ProtocolHead;

/**
 * @author ningyu
 * @date 2017年5月17日 下午5:08 <br/>
 */
public class GetMetadataRequest extends BaseRequest {
    /**
     * 组名
     */
    @FastDFSColumn(index = 0, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private String groupName;

    /**
     * 路径名
     */
    @FastDFSColumn(index = 1, dynamicField = DynamicFieldType.allRestByte)
    private String path;

    /**
     * 删除文件命令
     *
     * @param groupName 组名
     * @param path      文件路径
     */
    public GetMetadataRequest(String groupName, String path) {
        super();
        this.groupName = groupName;
        this.path = path;
        this.head = new ProtocolHead(CmdConstants.STORAGE_PROTO_CMD_GET_METADATA);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}