package io.github.ningyu.fastdfs.conn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.ningyu.fastdfs.constant.CmdConstants;
import io.github.ningyu.fastdfs.constant.OtherConstants;
import io.github.ningyu.fastdfs.exception.FastDfsConnectException;
import io.github.ningyu.fastdfs.utils.BytesUtil;
import io.github.ningyu.fastdfs.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 默认连接实现
 * <p>
 * @author ningyu
 * @date 2017年5月17日 下午4:20:00 <br/>
 */
public class SocketConnection implements Connection {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(SocketConnection.class);

    /**
     * 封装socket
     */
    private Socket socket;

    /**
     * 字符集
     */
    private Charset charset;

    /**
     * 创建与服务端连接
     *
     * @param address        连接地址
     * @param soTimeout      soTimeout
     * @param connectTimeout 设置连接超时
     */
    public SocketConnection(InetSocketAddress address, int soTimeout, int connectTimeout, Charset charset) {
        try {
            socket = new Socket();
            socket.setSoTimeout(soTimeout);
            logger.debug("开始连接到服务器 {} soTimeout={} connectTimeout={}", address, soTimeout, connectTimeout);
            this.charset = charset;
            socket.connect(address, connectTimeout);
            logger.debug("成功连接到服务器:{}", address);
        } catch (IOException e) {
            throw new FastDfsConnectException("不能连接到服务器:" + address, e);
        }
    }

    /**
     * 正常关闭连接
     */
    public synchronized void close() {
        logger.debug("断开连接, 服务器地址:{}", socket);
        byte[] header = new byte[OtherConstants.FDFS_PROTO_PKG_LEN_SIZE + 2];
        Arrays.fill(header, (byte) 0);
        byte[] hex_len = BytesUtil.long2buff(0);
        System.arraycopy(hex_len, 0, header, 0, hex_len.length);
        header[OtherConstants.PROTO_HEADER_CMD_INDEX] = CmdConstants.FDFS_PROTO_CMD_QUIT;
        header[OtherConstants.PROTO_HEADER_STATUS_INDEX] = (byte) 0;
        try {
            socket.getOutputStream().write(header);
            socket.close();
        } catch (IOException e) {
            logger.error("关闭连接失败", e);
        } finally {
            IOUtils.closeQuietly(socket);
        }
    }

    /**
     * 连接是否关闭
     */
    @Override
    public boolean isClosed() {
        return socket.isClosed();
    }

    /**
     * 检查连接是否有效
     */
    @Override
    public boolean isValid() {
        logger.debug("检查连接状态 {} ", this.socket);
        try {
            byte[] header = new byte[OtherConstants.FDFS_PROTO_PKG_LEN_SIZE + 2];
            Arrays.fill(header, (byte) 0);

            byte[] hex_len = BytesUtil.long2buff(0);
            System.arraycopy(hex_len, 0, header, 0, hex_len.length);
            header[OtherConstants.PROTO_HEADER_CMD_INDEX] = CmdConstants.FDFS_PROTO_CMD_ACTIVE_TEST;
            header[OtherConstants.PROTO_HEADER_STATUS_INDEX] = (byte) 0;
            socket.getOutputStream().write(header);
            if (socket.getInputStream().read(header) != header.length) {
                return false;
            }
            return header[OtherConstants.PROTO_HEADER_STATUS_INDEX] == 0;
        } catch (IOException e) {
            logger.error("检查连接状态异常", e);
            return false;
        }
    }

    /**
     * 获取输出流
     */
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    /**
     * 获取输入流
     */
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    /**
     * 获取字符集
     */
    public Charset getCharset() {
        return charset;
    }
}