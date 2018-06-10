package io.github.ningyu.fastdfs.protocol.tracker.request;

import io.github.ningyu.fastdfs.constant.CmdConstants;
import io.github.ningyu.fastdfs.protocol.BaseRequest;
import io.github.ningyu.fastdfs.protocol.ProtocolHead;

/**
 * 获取Group信息请求
 * @author ningyu
 * @date 2017年5月17日 下午3:06 <br/>
 */
public class GetGroupListRequest extends BaseRequest {
    public GetGroupListRequest() {
        head = new ProtocolHead(CmdConstants.TRACKER_PROTO_CMD_SERVER_LIST_GROUP);
    }
}
