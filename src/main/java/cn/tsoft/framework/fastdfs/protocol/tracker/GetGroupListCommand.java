package cn.tsoft.framework.fastdfs.protocol.tracker;

import cn.tsoft.framework.fastdfs.model.GroupState;
import cn.tsoft.framework.fastdfs.protocol.tracker.request.GetGroupListRequest;
import cn.tsoft.framework.fastdfs.protocol.tracker.response.GetGroupListResponse;

import java.util.List;

/**
 * 获取Group信息命令
 * @author ningyu
 * @date 2017年5月17日 下午3:05 <br/>
 */
public class GetGroupListCommand extends TrackerCommand<List<GroupState>> {

    public GetGroupListCommand() {
        super.request = new GetGroupListRequest();
        super.response = new GetGroupListResponse();
    }
}
