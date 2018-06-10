package io.github.ningyu.fastdfs.protocol.storage.response;

import io.github.ningyu.fastdfs.protocol.BaseResponse;
import io.github.ningyu.fastdfs.protocol.storage.callback.DownloadCallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

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
    	// 如果有内容
        if (getContentLength() > 0) {
            // 获取数据
            byte[] bytes = new byte[(int) getContentLength()];
            int contentSize = IOUtils.read(in, bytes);
            if (contentSize != getContentLength()) {
                throw new IOException("读取到的数据长度与协议长度不符");
            }
            FastDFSInputStream input = new FastDFSInputStream(new ByteArrayInputStream(bytes), getContentLength());
            return callback.receive(input);
        } else {
            FastDFSInputStream input = new FastDFSInputStream(new ByteArrayInputStream(new byte[0]), 0);
            return callback.receive(input);
        }
//        // 解析报文内容
//        FastDFSInputStream input = new FastDFSInputStream(in, getContentLength());
//        return callback.receive(input);
    }
}

