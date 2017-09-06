package cn.tsoft.framework.fastdfs.protocol.tracker.request;

import cn.tsoft.framework.fastdfs.constant.CmdConstants;
import cn.tsoft.framework.fastdfs.protocol.BaseRequest;
import cn.tsoft.framework.fastdfs.protocol.ProtocolHead;

/**
 * 获取存储节点请求
 * @author ningyu
 * @date 2017年5月17日 下午3:23 <br/>
 */
public class GetStorageNodeRequest extends BaseRequest {
    private static final byte withoutGroupCmd = CmdConstants.TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITHOUT_GROUP_ONE;

    /**
     * 获取存储节点
     */
    public GetStorageNodeRequest() {
        super();
        this.head = new ProtocolHead(withoutGroupCmd);
    }
}
