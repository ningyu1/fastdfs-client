package cn.tsoft.framework.fastdfs.protocol;

import cn.tsoft.framework.fastdfs.conn.Connection;

/**
 * FastDFS命令操执行接口
 * @author ningyu
 * @date 2017年5月17日 下午4:45:44
 */
public interface BaseCommand<T> {

    /**
     * 执行FastDFS命令
     */
    T execute(Connection conn);
}
