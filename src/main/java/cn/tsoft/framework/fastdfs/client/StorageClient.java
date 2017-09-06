package cn.tsoft.framework.fastdfs.client;

import java.io.InputStream;
import java.util.Set;

import cn.tsoft.framework.fastdfs.model.FileInfo;
import cn.tsoft.framework.fastdfs.model.MateData;
import cn.tsoft.framework.fastdfs.model.StorePath;
import cn.tsoft.framework.fastdfs.protocol.storage.callback.DownloadCallback;

/**
 * 存储服务(Storage)客户端接口
 * 
 * @author ningyu
 * @date 2017年5月18日 上午11:25:03
 *
 */
public interface StorageClient {
    /**
     * 上传文件
     *
     * @param groupName   组名称
     * @param inputStream 文件输入流
     * @param fileSize    文件大小
     * @param fileExtName 文件扩展名
     * @return 文件存储路径
     */
    StorePath uploadFile(String groupName, InputStream inputStream, long fileSize, String fileExtName);
    
    /**
     * 文件上传
     *
     * @param inputStream 文件输入流
     * @param fileSize    文件大小
     * @param fileExtName 文件扩展名
     * @return
     */
    StorePath uploadFile(InputStream inputStream, long fileSize, String fileExtName);
    
    
    /**
     * 文件上传
     *
     * @param localFilePath 文件完全路径
     * @return
     */
    StorePath uploadFile(String localFilePath);
    
    /**
     * 文件上传
     *
     * @param localFilePath 文件完全路径
     * @param fileExtName   文件后缀名
     * @return
     */
    StorePath uploadFile(String localFilePath, String fileExtName);
    
    /**
     * 文件上传
     *
     * @param groupName     组名称
     * @param localFilePath 文件完全路径
     * @param fileExtName   文件后缀名
     * @return
     */
    StorePath uploadFile(String groupName, String localFilePath, String fileExtName);

    /**
     * 上传从文件
     *
     * @param groupName      组名称
     * @param masterFilename 主文件路径(fastdfs返回的file_id 去掉前面的group)
     * @param inputStream    从文件输入流
     * @param fileSize       从文件大小
     * @param prefixName     从文件前缀
     * @param fileExtName    主文件扩展名
     * @return 文件存储路径
     */
    StorePath uploadSlaveFile(String groupName, String masterFilename, InputStream inputStream, long fileSize, String prefixName, String fileExtName);
   
    /**
     * 上传从文件
     *
     * @param masterFileId 主文件路径（fastdfs返回的file_id，包含前面的group）
     * @param inputStream  从文件输入流
     * @param fileSize     从文件大小
     * @param prefixName   从文件前缀
     * @param fileExtName  主文件扩展名
     * @return
     */
    StorePath uploadSlaveFile(String masterFileId, InputStream inputStream, long fileSize, String prefixName, String fileExtName);

    /**
     * 获取文件元信息
     *
     * @param groupName 组名称
     * @param path      主文件路径
     * @return 获取文件元信息集合，不存在返回空集合
     */
    Set<MateData> getMetadata(String groupName, String path);
    
    /**
     * 获取文件元信息
     *
     * @param fileId 文件路径（fastdfs返回的file_id，包含前面的group）
     * @return
     */
    Set<MateData> getMetadata(String fileId);

    /**
     * 修改文件元信息（覆盖）
     *
     * @param groupName   组名称
     * @param path        主文件路径
     * @param metaDataSet 元信息集合
     */
    boolean overwriteMetadata(String groupName, String path, Set<MateData> metaDataSet);
    
    /**
     * 修改文件元信息（覆盖）
     *
     * @param fileId        文件路径（fastdfs返回的file_id，包含前面的group）
     * @param metaDataSet   元信息集合
     * @return
     */
    boolean overwriteMetadata(String fileId, Set<MateData> metaDataSet);

    /**
     * 修改文件元信息（合并）
     *
     * @param groupName   组名称
     * @param path        主文件路径
     * @param metaDataSet 元信息集合
     */
    boolean mergeMetadata(String groupName, String path, Set<MateData> metaDataSet);
    
    /**
     * 修改文件元信息（合并）
     *
     * @param fileId         文件路径（fastdfs返回的file_id，包含前面的group）
     * @param metaDataSet    元信息集合
     * @return
     */
    boolean mergeMetadata(String fileId, Set<MateData> metaDataSet);

    /**
     * 获取文件的信息
     *
     * @param groupName 组名称
     * @param path      主文件路径
     * @return 文件信息(不存在返回null)
     */
    FileInfo getFileInfo(String groupName, String path);
    
    /**
     * 获取文件信息
     *
     * @param fileId  文件路径（fastdfs返回的file_id，包含前面的group）
     * @return
     */
    FileInfo getFileInfo(String fileId);

    /**
     * 删除文件
     *
     * @param groupName 组名称
     * @param path      主文件路径
     */
    boolean deleteFile(String groupName, String path);
    
    /**
     * 删除文件
     *
     * @param fileId 文件路径（fastdfs返回的file_id，包含前面的group）
     * @return
     */
    boolean deleteFile(String fileId);

    /**
     * 下载整个文件
     *
     * @param groupName 组名称
     * @param path      主文件路径
     * @param callback  下载回调接口
     * @return 下载回调接口返回结果
     */
    <T> T downloadFile(String groupName, String path, DownloadCallback<T> callback);
    
    /**
     * 下载整个文件
     *
     * @param fileId   文件路径（fastdfs返回的file_id，包含前面的group）
     * @param callback 下载回调接口
     * @return
     */
    <T> T downloadFile(String fileId, DownloadCallback<T> callback);

    /**
     * 下载文件片段
     *
     * @param groupName  组名称
     * @param path       主文件路径
     * @param fileOffset 开始位置
     * @param fileSize   文件大小(经过测试好像这个参数值只能是“0”)
     * @param callback   下载回调接口
     * @return 下载回调接口返回结果
     */
    <T> T downloadFile(String groupName, String path, long fileOffset, long fileSize, DownloadCallback<T> callback);
    
    /**
     * 下载文件片段
     *
     * @param fileId     文件路径（fastdfs返回的file_id，包含前面的group）
     * @param fileOffset 开始位置
     * @param fileSize   文件大小(经过测试好像这个参数值只能是“0”)
     * @param callback   下载回调接口
     * @return
     */
    <T> T downloadFile(String fileId, long fileOffset, long fileSize, DownloadCallback<T> callback);

    // ----------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 上传文件， 并设置文件元数据
     *
     * @param inputStream 文件输入流
     * @param fileSize    文件大小
     * @param fileExtName 文件扩展名
     * @param metaDataSet 元信息集合
     * @return 文件存储路径
     */
    StorePath uploadFile(InputStream inputStream, long fileSize, String fileExtName, Set<MateData> metaDataSet);
    
    /**
     * 上传文件， 并设置文件元数据
     *
     * @param groupName    组名
     * @param inputStream  文件输入流
     * @param fileSize     文件大小
     * @param fileExtName  文件扩展名
     * @param metaDataSet  元信息集合
     * @return
     */
    StorePath uploadFile(String groupName, InputStream inputStream, long fileSize, String fileExtName, Set<MateData> metaDataSet);

    /**
     * 文件上传(支持续传追加内容)
     *
     * @param groupName   组名称
     * @param inputStream 文件输入流(文件部分)
     * @param fileSize    文件大小
     * @param fileExtName 文件扩展名
     * @return 文件存储路径
     */
    StorePath uploadAppenderFile(String groupName, InputStream inputStream, long fileSize, String fileExtName);

    /**
     * 续传文件(追加内容)</br>
     * 从末尾追加内容</br>
     *
     * @param groupName   组名称
     * @param path        文件路径
     * @param inputStream 文件输入流(文件部分)
     * @param fileSize    文件大小
     * 
     */
    void appendFile(String groupName, String path, InputStream inputStream, long fileSize);
    
    /**
     * 续传文件(追加内容)</br>
     * 从末尾追加内容</br>
     *
     * @param fileId       文件路径（fastdfs返回的file_id，包含前面的group）
     * @param inputStream  文件输入流(文件部分)
     * @param fileSize     文件大小
     * 
     */
    void appendFile(String fileId, InputStream inputStream, long fileSize);

    /**
     * 修改文件内容的内容</br>
     * 从offset开始覆盖fileSize长度</br>
     * 报22参数错误，请检查offset是否超过文件长度</br>
     *
     * @param groupName   组名称
     * @param path        文件路径
     * @param inputStream 文件输入流
     * @param fileSize    文件大小
     * @param fileOffset  开始位置
     * 
     */
    void modifyFile(String groupName, String path, InputStream inputStream, long fileSize, long fileOffset);
    
    /**
     * 修改文件内容的内容</br>
     * 从offset开始覆盖fileSize长度</br>
     * 报22参数错误，请检查offset是否超过文件长度</br>
     *
     * @param fileId      文件路径（fastdfs返回的file_id，包含前面的group）
     * @param inputStream 文件输入流
     * @param fileSize    文件大小
     * @param fileOffset  开始位置
     * 
     */
    void modifyFile(String fileId, InputStream inputStream, long fileSize, long fileOffset);

    /**
     * 清除文件的内容
     *
     * @param groupName         组名称
     * @param path              文件路径
     * @param truncatedFileSize 截断文件大小
     * 
     */
    void truncateFile(String groupName, String path, long truncatedFileSize);
    
    /**
     * 清除文件的内容
     *
     * @param fileId            文件路径（fastdfs返回的file_id，包含前面的group）
     * @param truncatedFileSize 截断文件大小
     * 
     */
    void truncateFile(String fileId, long truncatedFileSize);

    /**
     * 清除文件的内容
     *
     * @param groupName 组名称
     * @param path      文件路径
     * 
     */
    void truncateFile(String groupName, String path);
    
    /**
     * 清除文件的内容
     *
     * @param fileId 文件路径（fastdfs返回的file_id，包含前面的group）
     * 
     */
    void truncateFile(String fileId);
}
