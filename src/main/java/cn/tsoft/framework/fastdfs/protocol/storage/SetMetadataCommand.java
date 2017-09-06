package cn.tsoft.framework.fastdfs.protocol.storage;

import cn.tsoft.framework.fastdfs.model.MateData;
import cn.tsoft.framework.fastdfs.protocol.BaseResponse;
import cn.tsoft.framework.fastdfs.protocol.storage.enums.StorageMetadataSetType;
import cn.tsoft.framework.fastdfs.protocol.storage.request.SetMetadataRequest;

import java.util.Set;

/**
 * 设置文件标签(文件元数据)
 * @author ningyu
 * @date 2017年5月17日 下午6:40 <br/>
 */
public class SetMetadataCommand extends StorageCommand<Void> {

    /**
     * 设置文件标签(元数据)
     *
     * @param groupName   组名称
     * @param path        路径
     * @param metaDataSet 元数据集合
     * @param type        增加元数据的类型
     */
    public SetMetadataCommand(String groupName, String path, Set<MateData> metaDataSet, StorageMetadataSetType type) {
        this.request = new SetMetadataRequest(groupName, path, metaDataSet, type);
        // 输出响应
        this.response = new BaseResponse<Void>() {
        };
    }
}
