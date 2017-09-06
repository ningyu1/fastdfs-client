package cn.tsoft.framework.fastdfs.protocol.tracker.request;

import cn.tsoft.framework.fastdfs.constant.CmdConstants;
import cn.tsoft.framework.fastdfs.constant.OtherConstants;
import cn.tsoft.framework.fastdfs.mapper.FastDFSColumn;
import cn.tsoft.framework.fastdfs.protocol.BaseRequest;
import cn.tsoft.framework.fastdfs.protocol.ProtocolHead;
import cn.tsoft.framework.fastdfs.utils.Validate;

/**
 * 删除存储服务器 请求
 * @author ningyu
 * @date 2017年5月17日 下午3:44 <br/>
 */
public class DeleteStorageRequest extends BaseRequest {
    /**
     * 组名
     */
    @FastDFSColumn(index = 0, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private String groupName;

    /**
     * 存储ip
     */
    @FastDFSColumn(index = 1, max = OtherConstants.FDFS_IPADDR_SIZE - 1)
    private String storageIpAddr;

    /**
     * 获取文件源服务器
     *
     * @param groupName     组名称
     * @param storageIpAddr 存储服务器IP地址
     */
    public DeleteStorageRequest(String groupName, String storageIpAddr) {
        Validate.notBlank(groupName, "分组不能为空");
        Validate.notBlank(storageIpAddr, "文件路径不能为空");
        this.groupName = groupName;
        this.storageIpAddr = storageIpAddr;
        head = new ProtocolHead(CmdConstants.TRACKER_PROTO_CMD_SERVER_DELETE_STORAGE);
    }

    public String getGroupName() {
        return groupName;
    }

    public String getStorageIpAddr() {
        return storageIpAddr;
    }

}
