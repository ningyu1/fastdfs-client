package io.github.ningyu.fastdfs.protocol;

import io.github.ningyu.fastdfs.conn.Connection;

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
