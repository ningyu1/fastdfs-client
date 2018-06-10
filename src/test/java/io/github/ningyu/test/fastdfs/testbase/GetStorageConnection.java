package io.github.ningyu.test.fastdfs.testbase;

import io.github.ningyu.fastdfs.conn.Connection;
import io.github.ningyu.fastdfs.conn.SocketConnection;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author ningyu
 * @date 2017年5月18日 中午12:51 <br/>
 */
public class GetStorageConnection {
    private static final InetSocketAddress address = new InetSocketAddress("192.168.0.48", 23000);
    private static final int soTimeout = 1500;
    private static final int connectTimeout = 600;
    private static final Charset charset = Charset.forName("UTF-8");

    public static Connection getDefaultConnection() {
        return new SocketConnection(address, soTimeout, connectTimeout, charset);
    }
}
