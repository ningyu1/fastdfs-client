package io.github.ningyu.fastdfs.exception;

/**
 * 非FastDFS本身的错误码抛出的异常，取服务端连接取不到时抛出的异常
 * @author ningyu
 * @date 2017年5月17日 下午4:28:18
 */
public class FastDfsUnavailableException extends FastDfsException {
    /**
     */
    private static final long serialVersionUID = 8966427134904775501L;

    public FastDfsUnavailableException(String message) {
        super("无法获取服务端连接资源：" + message);
    }

    public FastDfsUnavailableException(String message, Throwable t) {
        super("无法获取服务端连接资源：" + message, t);
    }
}