package io.github.ningyu.fastdfs.exception;

/**
 * 非FastDFS本身的错误码抛出的异常，socket连不上时抛出的异常
 * @author ningyu
 * @date 2017年5月17日 下午4:22:44
 */
public class FastDfsConnectException extends FastDfsUnavailableException {
    /**
     */
    private static final long serialVersionUID = 8398487620096735414L;

    public FastDfsConnectException(String message, Throwable t) {
        super(message, t);
    }
}