package io.github.ningyu.fastdfs.exception;

/**
 * FastDFS客户端异常 基类
 * <p>
 * @author ningyu
 * @date 2017年5月17日 下午4:22:59
 */
public class FastDfsException extends RuntimeException {
    /**
     */
    private static final long serialVersionUID = 1912248014970005491L;

    public FastDfsException(String message) {
        super(message);
    }

    public FastDfsException(String message, Throwable cause) {
        super(message, cause);
    }
}

