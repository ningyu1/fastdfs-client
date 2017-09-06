package cn.tsoft.framework.fastdfs.exception;

/**
 * 不支持的图片格式
 * @author ningyu
 * @date 2017年5月17日 下午4:28:31
 */
public class FastDfsUnsupportImageTypeException extends FastDfsException {
    /**
     */
    private static final long serialVersionUID = 2690221596264307678L;

    public FastDfsUnsupportImageTypeException(String message) {
        super(message);
    }
}
