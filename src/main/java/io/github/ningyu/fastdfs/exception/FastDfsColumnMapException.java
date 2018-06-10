package io.github.ningyu.fastdfs.exception;

/**
 * 映射异常
 * @author ningyu
 * @date 2017年5月17日 下午4:22:20
 */
public class FastDfsColumnMapException extends RuntimeException {
    /**
     */
    private static final long serialVersionUID = -8200516349192931147L;

    public FastDfsColumnMapException() {
    }

    public FastDfsColumnMapException(String message, Throwable cause) {
        super(message, cause);
    }

    public FastDfsColumnMapException(String message) {
        super(message);
    }

    public FastDfsColumnMapException(Throwable cause) {
        super(cause);
    }
}