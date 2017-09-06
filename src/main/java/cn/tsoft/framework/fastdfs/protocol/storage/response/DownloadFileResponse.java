package cn.tsoft.framework.fastdfs.protocol.storage.response;

import cn.tsoft.framework.fastdfs.protocol.BaseResponse;
import cn.tsoft.framework.fastdfs.protocol.storage.callback.DownloadCallback;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author ningyu
 * @date 2017年5月17日 下午4:06 <br/>
 */
public class DownloadFileResponse<T> extends BaseResponse<T> {

    private DownloadCallback<T> callback;

    public DownloadFileResponse(DownloadCallback<T> callback) {
        super();
        this.callback = callback;
    }

    /**
     * 解析反馈内容
     */
    @Override
    public T decodeContent(InputStream in, Charset charset) throws IOException {
        // 解析报文内容
        FastDFSInputStream input = new FastDFSInputStream(in, getContentLength());
        return callback.receive(input);
    }
}

