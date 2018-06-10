package io.github.ningyu.fastdfs.client;

import io.github.ningyu.fastdfs.conn.CommandExecutor;
import io.github.ningyu.fastdfs.constant.ErrorCodeConstants;
import io.github.ningyu.fastdfs.constant.OtherConstants;
import io.github.ningyu.fastdfs.exception.FastDfsServerException;
import io.github.ningyu.fastdfs.model.FileInfo;
import io.github.ningyu.fastdfs.model.MateData;
import io.github.ningyu.fastdfs.model.StorageNode;
import io.github.ningyu.fastdfs.model.StorageNodeInfo;
import io.github.ningyu.fastdfs.model.StorePath;
import io.github.ningyu.fastdfs.protocol.storage.AppendFileCommand;
import io.github.ningyu.fastdfs.protocol.storage.DeleteFileCommand;
import io.github.ningyu.fastdfs.protocol.storage.DownloadFileCommand;
import io.github.ningyu.fastdfs.protocol.storage.GetMetadataCommand;
import io.github.ningyu.fastdfs.protocol.storage.ModifyCommand;
import io.github.ningyu.fastdfs.protocol.storage.QueryFileInfoCommand;
import io.github.ningyu.fastdfs.protocol.storage.SetMetadataCommand;
import io.github.ningyu.fastdfs.protocol.storage.TruncateCommand;
import io.github.ningyu.fastdfs.protocol.storage.UploadFileCommand;
import io.github.ningyu.fastdfs.protocol.storage.UploadSlaveFileCommand;
import io.github.ningyu.fastdfs.protocol.storage.callback.DownloadCallback;
import io.github.ningyu.fastdfs.protocol.storage.enums.StorageMetadataSetType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 存储服务(Storage)客户端接口 默认实现<br/>
 * <b>注意: 当前类最好使用单例，一个应用只需要一个实例</b>
 * 
 * @author ningyu
 * @date 2017年5月17日 下午4:16:58
 *
 */
public class DefaultStorageClient implements StorageClient {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(DefaultStorageClient.class);

    private CommandExecutor commandExecutor;

    private TrackerClient trackerClient;

    public DefaultStorageClient(CommandExecutor commandExecutor, TrackerClient trackerClient) {
        this.commandExecutor = commandExecutor;
        this.trackerClient = trackerClient;
    }

    @Override
    public StorePath uploadFile(String groupName, InputStream inputStream, long fileSize, String fileExtName) {
        StorageNode storageNode;
        if(StringUtils.isBlank(groupName)) {
            storageNode = trackerClient.getStorageNode();
        } else {
            storageNode = trackerClient.getStorageNode(groupName);
        }
        UploadFileCommand command = new UploadFileCommand(storageNode.getStoreIndex(), inputStream, fileExtName, fileSize, false);
        return commandExecutor.execute(storageNode.getInetSocketAddress(), command);
    }
    
    @Override
    public StorePath uploadFile(InputStream inputStream, long fileSize, String fileExtName) {
        return uploadFile(null, inputStream, fileSize, fileExtName);
    }
    
    @Override
    public StorePath uploadFile(String localFilePath) {
        return uploadFile(localFilePath, localFilePath.substring(localFilePath.lastIndexOf(".")+1));
    }
    
    @Override
    public StorePath uploadFile(String localFilePath, String fileExtName) {
        return uploadFile(null, localFilePath, fileExtName);
    }
    
    @Override
    public StorePath uploadFile(String groupName, String localFilePath, String fileExtName) {
        File file = new File(localFilePath);
        FileInputStream fileInputStream;
        try {
            fileInputStream = FileUtils.openInputStream(file);
        } catch (IOException e) {
            throw new IllegalArgumentException("localFilePath is not found!");
        }
        if(StringUtils.isBlank(fileExtName)) {
            throw new IllegalArgumentException("file ext name is not found!");
        }
        return uploadFile(groupName, fileInputStream, file.length(), fileExtName);
    }

    @Override
    public StorePath uploadSlaveFile(String groupName, String masterFilename, InputStream inputStream, long fileSize, String prefixName, String fileExtName) {
        StorageNodeInfo storageNodeInfo = trackerClient.getFetchStorageAndUpdate(groupName, masterFilename);
        UploadSlaveFileCommand command = new UploadSlaveFileCommand(inputStream, fileSize, masterFilename, prefixName, fileExtName);
        return commandExecutor.execute(storageNodeInfo.getInetSocketAddress(), command);
    }
    
    @Override
    public StorePath uploadSlaveFile(String masterFileId, InputStream inputStream, long fileSize, String prefixName, String fileExtName) {
        String[] parts = new String[2];
        splitFileId(masterFileId, parts);
        StorageNodeInfo storageNodeInfo = trackerClient.getFetchStorageAndUpdate(parts[0], parts[1]);
        UploadSlaveFileCommand command = new UploadSlaveFileCommand(inputStream, fileSize, parts[1], prefixName, fileExtName);
        return commandExecutor.execute(storageNodeInfo.getInetSocketAddress(), command);
    }
    
    public void splitFileId(String fileId, String[] results) {
        int pos = fileId.indexOf(OtherConstants.SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR);
        if ((pos <= 0) || (pos == fileId.length() - 1)) {
            throw new IllegalArgumentException("file_id is invalid path!");
        }
        results[0] = fileId.substring(0, pos); // group name
        results[1] = fileId.substring(pos + 1); // file name
    }

    @Override
    public Set<MateData> getMetadata(String groupName, String path) {
        try {
            StorageNodeInfo storageNodeInfo = trackerClient.getFetchStorage(groupName, path);
            GetMetadataCommand command = new GetMetadataCommand(groupName, path);
            return commandExecutor.execute(storageNodeInfo.getInetSocketAddress(), command);
        } catch (Throwable e) {
            logger.error("获取文件元信息", e);
            return new HashSet<MateData>();
        }
    }
    
    @Override
    public Set<MateData> getMetadata(String fileId) {
        String[] parts = new String[2];
        splitFileId(fileId, parts);
        return getMetadata(parts[0], parts[1]);
    }

    @Override
    public boolean overwriteMetadata(String groupName, String path, Set<MateData> metaDataSet) {
        try {
            StorageNodeInfo storageNodeInfo = trackerClient.getFetchStorageAndUpdate(groupName, path);
            SetMetadataCommand command = new SetMetadataCommand(groupName, path, metaDataSet, StorageMetadataSetType.STORAGE_SET_METADATA_FLAG_OVERWRITE);
            commandExecutor.execute(storageNodeInfo.getInetSocketAddress(), command);
        } catch (Throwable e) {
            logger.error("修改文件元信息（覆盖）失败", e);
            return false;
        }
        return true;
    }
    
    @Override
    public boolean overwriteMetadata(String fileId, Set<MateData> metaDataSet) {
        String[] parts = new String[2];
        splitFileId(fileId, parts);
        return overwriteMetadata(parts[0], parts[1], metaDataSet);
    }

    @Override
    public boolean mergeMetadata(String groupName, String path, Set<MateData> metaDataSet) {
        try {
            StorageNodeInfo storageNodeInfo = trackerClient.getFetchStorageAndUpdate(groupName, path);
            SetMetadataCommand command = new SetMetadataCommand(groupName, path, metaDataSet, StorageMetadataSetType.STORAGE_SET_METADATA_FLAG_MERGE);
            commandExecutor.execute(storageNodeInfo.getInetSocketAddress(), command);
        } catch (Throwable e) {
            logger.error("修改文件元信息（合并）失败", e);
            return false;
        }
        return true;
    }
    
    @Override
    public boolean mergeMetadata(String fileId, Set<MateData> metaDataSet) {
        String[] parts = new String[2];
        splitFileId(fileId, parts);
        return mergeMetadata(parts[0], parts[1], metaDataSet);
    }

    @Override
    public FileInfo getFileInfo(String groupName, String path) {
        FileInfo fileInfo = null;
        StorageNodeInfo storageNodeInfo = trackerClient.getFetchStorage(groupName, path);
        QueryFileInfoCommand command = new QueryFileInfoCommand(groupName, path);
        try {
            fileInfo = commandExecutor.execute(storageNodeInfo.getInetSocketAddress(), command);
        } catch (FastDfsServerException e) {
            if (e.getErrorCode() == ErrorCodeConstants.ERR_NO_ENOENT) {
                logger.warn("获取文件的信息异常,ErrorCode=[{}], ErrorMessage=[{}]", e.getErrorCode(), e.getMessage());
            } else {
                throw e;
            }
        }
        return fileInfo;
    }
    
    @Override
    public FileInfo getFileInfo(String fileId) {
        String[] parts = new String[2];
        splitFileId(fileId, parts);
        return getFileInfo(parts[0], parts[1]);
    }

    @Override
    public boolean deleteFile(String groupName, String path) {
        try {
            StorageNodeInfo storageNodeInfo = trackerClient.getFetchStorageAndUpdate(groupName, path);
            DeleteFileCommand command = new DeleteFileCommand(groupName, path);
            commandExecutor.execute(storageNodeInfo.getInetSocketAddress(), command);
        } catch (Throwable e) {
            logger.error("删除文件失败", e);
            return false;
        }
        return true;
    }
    
    @Override
    public boolean deleteFile(String fileId) {
        String[] parts = new String[2];
        splitFileId(fileId, parts);
        return deleteFile(parts[0], parts[1]);
    }

    @Override
    public <T> T downloadFile(String groupName, String path, DownloadCallback<T> callback) {
        long fileOffset = 0;
        long fileSize = 0;
        return downloadFile(groupName, path, fileOffset, fileSize, callback);
    }
    
    @Override
    public <T> T downloadFile(String fileId, DownloadCallback<T> callback) {
        String[] parts = new String[2];
        splitFileId(fileId, parts);
        return downloadFile(parts[0], parts[1], callback);
    }

    @Override
    public <T> T downloadFile(String groupName, String path, long fileOffset, long fileSize, DownloadCallback<T> callback) {
        StorageNodeInfo storageNodeInfo = trackerClient.getFetchStorage(groupName, path);
        DownloadFileCommand<T> command = new DownloadFileCommand<T>(groupName, path, fileOffset, fileSize, callback);
        return commandExecutor.execute(storageNodeInfo.getInetSocketAddress(), command);
    }
    
    @Override
    public <T> T downloadFile(String fileId, long fileOffset, long fileSize, DownloadCallback<T> callback) {
        String[] parts = new String[2];
        splitFileId(fileId, parts);
        return downloadFile(parts[0], parts[1], fileOffset, fileSize, callback);
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public StorePath uploadFile(InputStream inputStream, long fileSize, String fileExtName, Set<MateData> metaDataSet) {
        return uploadFile(null, inputStream, fileSize, fileExtName, metaDataSet);
    }
    
    @Override
    public StorePath uploadFile(String groupName, InputStream inputStream, long fileSize, String fileExtName, Set<MateData> metaDataSet) {
        StorageNode storageNode;
        if(StringUtils.isBlank(groupName)) {
            storageNode = trackerClient.getStorageNode();
        } else {
            storageNode = trackerClient.getStorageNode(groupName);
        }
        UploadFileCommand command = new UploadFileCommand(storageNode.getStoreIndex(), inputStream, fileExtName, fileSize, false);
        StorePath storePath = commandExecutor.execute(storageNode.getInetSocketAddress(), command);
        if (metaDataSet == null || metaDataSet.size() <= 0) {
            return storePath;
        }
        SetMetadataCommand cmd = new SetMetadataCommand(storePath.getGroup(), storePath.getPath(), metaDataSet, StorageMetadataSetType.STORAGE_SET_METADATA_FLAG_OVERWRITE);
        commandExecutor.execute(storageNode.getInetSocketAddress(), cmd);
        return storePath;
    }

    @Override
    public StorePath uploadAppenderFile(String groupName, InputStream inputStream, long fileSize, String fileExtName) {
        StorageNode storageNode = trackerClient.getStorageNode(groupName);
        UploadFileCommand command = new UploadFileCommand(storageNode.getStoreIndex(), inputStream, fileExtName, fileSize, true);
        return commandExecutor.execute(storageNode.getInetSocketAddress(), command);
    }

    @Override
    public void appendFile(String groupName, String path, InputStream inputStream, long fileSize) {
        StorageNodeInfo storageNodeInfo = trackerClient.getFetchStorageAndUpdate(groupName, path);
        AppendFileCommand command = new AppendFileCommand(inputStream, fileSize, path);
        commandExecutor.execute(storageNodeInfo.getInetSocketAddress(), command);
    }
    
    @Override
    public void appendFile(String fileId, InputStream inputStream, long fileSize) {
        String[] parts = new String[2];
        splitFileId(fileId, parts);
        appendFile(parts[0], parts[1], inputStream, fileSize);
    }

    @Override
    public void modifyFile(String groupName, String path, InputStream inputStream, long fileSize, long fileOffset) {
        StorageNodeInfo storageNodeInfo = trackerClient.getFetchStorageAndUpdate(groupName, path);
        ModifyCommand command = new ModifyCommand(path, inputStream, fileSize, fileOffset);
        commandExecutor.execute(storageNodeInfo.getInetSocketAddress(), command);
    }
    
    @Override
    public void modifyFile(String fileId, InputStream inputStream, long fileSize, long fileOffset) {
        String[] parts = new String[2];
        splitFileId(fileId, parts);
        modifyFile(parts[0], parts[1], inputStream, fileSize, fileOffset);
    }

    @Override
    public void truncateFile(String groupName, String path, long truncatedFileSize) {
        StorageNodeInfo storageNodeInfo = trackerClient.getFetchStorageAndUpdate(groupName, path);
        TruncateCommand command = new TruncateCommand(path, truncatedFileSize);
        commandExecutor.execute(storageNodeInfo.getInetSocketAddress(), command);
    }
    
    @Override
    public void truncateFile(String fileId, long truncatedFileSize) {
        String[] parts = new String[2];
        splitFileId(fileId, parts);
        truncateFile(parts[0], parts[1], truncatedFileSize);
    }

    @Override
    public void truncateFile(String groupName, String path) {
        long truncatedFileSize = 0;
        truncateFile(groupName, path, truncatedFileSize);
    }
    
    @Override
    public void truncateFile(String fileId) {
        String[] parts = new String[2];
        splitFileId(fileId, parts);
        truncateFile(parts[0], parts[1]);
    }

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    public void setCommandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public TrackerClient getTrackerClient() {
        return trackerClient;
    }

    public void setTrackerClient(TrackerClient trackerClient) {
        this.trackerClient = trackerClient;
    }

}
