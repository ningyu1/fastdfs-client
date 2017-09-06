package cn.tsoft.framework.fastdfs.protocol.storage.request;

import cn.tsoft.framework.fastdfs.constant.CmdConstants;
import cn.tsoft.framework.fastdfs.constant.OtherConstants;
import cn.tsoft.framework.fastdfs.mapper.DynamicFieldType;
import cn.tsoft.framework.fastdfs.mapper.FastDFSColumn;
import cn.tsoft.framework.fastdfs.protocol.BaseRequest;
import cn.tsoft.framework.fastdfs.protocol.ProtocolHead;

/**
 * 下载文件请求
 * @author ningyu
 * @date 2017年5月17日 下午4:03 <br/>
 */
public class DownloadFileRequest extends BaseRequest {

    /**
     * 开始位置
     */
    @FastDFSColumn(index = 0)
    private long fileOffset;
    /**
     * 读取文件长度
     */
    @FastDFSColumn(index = 1)
    private long fileSize;
    /**
     * 组名
     */
    @FastDFSColumn(index = 2, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private String groupName;
    /**
     * 文件路径
     */
    @FastDFSColumn(index = 3, dynamicField = DynamicFieldType.allRestByte)
    private String path;

    /**
     * 文件下载请求
     *
     * @param groupName  组名称
     * @param path       文件路径
     * @param fileOffset 开始位置
     * @param fileSize   读取文件长度
     */
    public DownloadFileRequest(String groupName, String path, long fileOffset, long fileSize) {
        super();
        this.groupName = groupName;
        this.fileSize = fileSize;
        this.path = path;
        this.fileOffset = fileOffset;
        head = new ProtocolHead(CmdConstants.STORAGE_PROTO_CMD_DOWNLOAD_FILE);
    }

    public long getFileOffset() {
        return fileOffset;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getPath() {
        return path;
    }

    @Override
    public long getFileSize() {
        return fileSize;
    }
}