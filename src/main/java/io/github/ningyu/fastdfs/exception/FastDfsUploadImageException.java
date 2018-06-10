package io.github.ningyu.fastdfs.exception;

/**
 * 上传图片异常
 * @author ningyu
 * @date 2017年5月17日 下午4:29:06
 */
public class FastDfsUploadImageException extends FastDfsException {
    /**
     */
    private static final long serialVersionUID = 5206141975201150208L;

    protected FastDfsUploadImageException(String message) {
        super(message);
    }

    public FastDfsUploadImageException(String message, Throwable cause) {
        super(message, cause);
    }
}
