package io.github.ningyu.fastdfs.pool;

import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

import io.github.ningyu.fastdfs.conn.Connection;

import java.net.InetSocketAddress;

/**
 * 定义FastDFS连接池对象<br/>
 * 定义了对象池要实现的功能,对一个地址进行池化Map Pool<br/>
 * @author ningyu
 * @date 2017年5月17日 下午4:44:53
 */
public class ConnectionPool extends GenericKeyedObjectPool<InetSocketAddress, Connection> {
    /**
     * 默认构造函数
     */
    public ConnectionPool(KeyedPooledObjectFactory<InetSocketAddress, Connection> factory, GenericKeyedObjectPoolConfig config) {
        super(factory, config);
    }

    /**
     * 默认构造函数
     */
    public ConnectionPool(KeyedPooledObjectFactory<InetSocketAddress, Connection> factory) {
        super(factory);
    }
}