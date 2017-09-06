package cn.tsoft.framework.fastdfs.protocol.storage.callback;


import cn.tsoft.framework.fastdfs.utils.IOUtils;

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