package io.github.ningyu.fastdfs.protocol.storage;

import io.github.ningyu.fastdfs.protocol.storage.callback.DownloadCallback;
import io.github.ningyu.fastdfs.protocol.storage.request.DownloadFileRequest;
import io.github.ningyu.fastdfs.protocol.storage.response.DownloadFileResponse;

/**
 * 下载文件
 * @author ningyu
 * @date 2017年5月17日 下午4:48:05
 */
public class DownloadFileCommand<T> extends StorageCommand<T> {

    /**
     * 下载文件
     *
     * @param groupName  组名称
     * @param path       文件路径
     * @param fileOffset 开始位置
     * @param fileSize   读取文件长度
     * @param callback   文件下载回调
     */
    public DownloadFileCommand(String groupName, String path, long fileOffset, long fileSize, DownloadCallback<T> callback) {
        super();
        this.request = new DownloadFileRequest(groupName, path, fileOffset, fileSize);
        // 输出响应
        this.response = new DownloadFileResponse<T>(callback);
    }

    /**
     * 下载文件
     *
     * @param groupName 组名称
     * @param path      文件路径
     * @param callback  文件下载回调
     */
    public DownloadFileCommand(String groupName, String path, DownloadCallback<T> callback) {
        super();
        this.request = new DownloadFileRequest(groupName, path, 0, 0);
        // 输出响应
        this.response = new DownloadFileResponse<T>(callback);
    }
}