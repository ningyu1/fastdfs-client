package cn.tsoft.framework.fastdfs.protocol.storage.request;

import cn.tsoft.framework.fastdfs.constant.CmdConstants;
import cn.tsoft.framework.fastdfs.constant.OtherConstants;
import cn.tsoft.framework.fastdfs.mapper.DynamicFieldType;
import cn.tsoft.framework.fastdfs.mapper.FastDFSColumn;
import cn.tsoft.framework.fastdfs.model.MateData;
import cn.tsoft.framework.fastdfs.protocol.BaseRequest;
import cn.tsoft.framework.fastdfs.protocol.ProtocolHead;
import cn.tsoft.framework.fastdfs.protocol.storage.enums.StorageMetadataSetType;
import cn.tsoft.framework.fastdfs.utils.MetadataMapperUtils;
import cn.tsoft.framework.fastdfs.utils.Validate;

import java.nio.charset.Charset;
import java.util.Set;

/**
 * @author ningyu
 * @date 2017年5月17日 下午6:46 <br/>
 */
public class SetMetadataRequest extends BaseRequest {
    /**
     * 文件名byte长度
     */
    @FastDFSColumn(index = 0)
    private int fileNameByteLength;

    /**
     * 元数据byte长度
     */
    @FastDFSColumn(index = 1)
    private int mataDataByteLength;

    /**
     * 操作标记（重写/覆盖）
     */
    @FastDFSColumn(index = 2)
    private byte opFlag;

    /**
     * 组名
     */
    @FastDFSColumn(index = 3, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private String groupName;

    /**
     * 文件路径
     */
    @FastDFSColumn(index = 4, dynamicField = DynamicFieldType.allRestByte)
    private String path;

    /**
     * 元数据
     */
    @FastDFSColumn(index = 5, dynamicField = DynamicFieldType.matedata)
    private Set<MateData> metaDataSet;

    /**
     * 设置文件元数据
     *
     * @param groupName   组名称
     * @param path        路径
     * @param metaDataSet 元数据集合
     * @param type        增加元数据的类型
     */
    public SetMetadataRequest(String groupName, String path, Set<MateData> metaDataSet, StorageMetadataSetType type) {
        super();
        Validate.notBlank(groupName, "分组不能为空");
        Validate.notBlank(path, "分组不能为空");
        Validate.notEmpty(metaDataSet, "分组不能为空");
        Validate.notNull(type, "标签设置方式不能为空");
        this.groupName = groupName;
        this.path = path;
        this.metaDataSet = metaDataSet;
        this.opFlag = type.getType();
        head = new ProtocolHead(CmdConstants.STORAGE_PROTO_CMD_SET_METADATA);
    }

    /**
     * 打包参数
     */
    @Override
    public byte[] encodeParam(Charset charset) {
        // 运行时参数在此计算值
        this.fileNameByteLength = path.getBytes(charset).length;
        this.mataDataByteLength = getMetaDataSetByteSize(charset);
        return super.encodeParam(charset);
    }

    /**
     * 获取metaDataSet长度
     */
    private int getMetaDataSetByteSize(Charset charset) {
        return MetadataMapperUtils.toByte(metaDataSet, charset).length;
    }

    public String getGroupName() {
        return groupName;
    }

    public Set<MateData> getMetaDataSet() {
        return metaDataSet;
    }

    public byte getOpFlag() {
        return opFlag;
    }

    public String getPath() {
        return path;
    }

    public int getFileNameByteLength() {
        return fileNameByteLength;
    }

    public int getMataDataByteLength() {
        return mataDataByteLength;
    }
}