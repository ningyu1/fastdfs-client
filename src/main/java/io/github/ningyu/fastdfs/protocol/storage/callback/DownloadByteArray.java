package io.github.ningyu.fastdfs.protocol.storage.callback;


import io.github.ningyu.fastdfs.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 直接返回Byte[]数据
 * @author ningyu
 * @date 2017年5月17日 下午3:56 <br/>
 */
public class DownloadByteArray implements DownloadCallback<byte[]> {
    @Override
    public byte[] receive(InputStream inputStream) throws IOException {
        return IOUtils.toByteArray(inputStream);
    }
}