package cn.tsoft.framework.fastdfs.protocol.tracker.request;

import cn.tsoft.framework.fastdfs.constant.CmdConstants;
import cn.tsoft.framework.fastdfs.constant.OtherConstants;
import cn.tsoft.framework.fastdfs.mapper.DynamicFieldType;
import cn.tsoft.framework.fastdfs.mapper.FastDFSColumn;
import cn.tsoft.framework.fastdfs.protocol.BaseRequest;
import cn.tsoft.framework.fastdfs.protocol.ProtocolHead;
import cn.tsoft.framework.fastdfs.utils.Validate;

/**
 * 获取文件源存储服务器 请求
 * @author ningyu
 * @date 2017年5月17日 下午3:32 <br/>
 */
public class GetFetchStorageRequest extends BaseRequest {
    private static final byte fetchCmd = CmdConstants.TRACKER_PROTO_CMD_SERVICE_QUERY_FETCH_ONE;
    private static final byte updateCmd = CmdConstants.TRACKER_PROTO_CMD_SERVICE_QUERY_UPDATE;

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
     * 获取文件源服务器
     *
     * @param groupName 组名称
     * @param path      路径
     * @param toUpdate  toUpdate
     */
    public GetFetchStorageRequest(String groupName, String path, boolean toUpdate) {
        Validate.notBlank(groupName, "分组不能为空");
        Validate.notBlank(path, "文件路径不能为空");
        this.groupName = groupName;
        this.path = path;
        if (toUpdate) {
            head = new ProtocolHead(updateCmd);
        } else {
            head = new ProtocolHead(fetchCmd);
        }
    }

    public String getGroupName() {
        return groupName;
    }

    public String getPath() {
        return path;
    }

}
