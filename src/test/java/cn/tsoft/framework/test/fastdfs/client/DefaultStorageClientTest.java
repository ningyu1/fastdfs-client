package cn.tsoft.framework.test.fastdfs.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import cn.tsoft.framework.fastdfs.client.DefaultStorageClient;
import cn.tsoft.framework.fastdfs.client.DefaultTrackerClient;
import cn.tsoft.framework.fastdfs.client.StorageClient;
import cn.tsoft.framework.fastdfs.client.TrackerClient;
import cn.tsoft.framework.fastdfs.conn.DefaultCommandExecutor;
import cn.tsoft.framework.fastdfs.constant.OtherConstants;
import cn.tsoft.framework.fastdfs.model.FileInfo;
import cn.tsoft.framework.fastdfs.model.MateData;
import cn.tsoft.framework.fastdfs.model.StorePath;
import cn.tsoft.framework.fastdfs.pool.ConnectionPool;
import cn.tsoft.framework.fastdfs.pool.PooledConnectionFactory;
import cn.tsoft.framework.fastdfs.protocol.storage.callback.DownloadFileWriter;

/**
 * @author ningyu
 * @date 2017年5月19日 下午2:22 <br/>
 */
public class DefaultStorageClientTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(DefaultTrackerClientTest.class);

    private static ConnectionPool connectionPool;
    private static StorageClient storageClient;
    private Set<String> resultFileId = new HashSet<String>();
    
    private void addResultFileId(String fileId) {
        resultFileId.add(fileId);
    }
    
    public void dumpPoolInfo() {
        if (logger.isDebugEnabled()) {
            String tmp = "\r\n" +
                    "#=======================================================================================================================#\r\n" +
                    "# ------Dump Pool Info------\r\n" +
                    "#\t 活动连接：" + connectionPool.getNumActive() + "\r\n" +
                    "#\t 空闲连接：" + connectionPool.getNumIdle() + "\r\n" +
                    "#\t 正在使用的连接：" + connectionPool.getNumWaiters() + "\r\n" +
                    "#\t 连接获取总数统计：" + connectionPool.getBorrowedCount() + "\r\n" +
                    "#\t 连接返回总数统计：" + connectionPool.getReturnedCount() + "\r\n" +
                    "#\t 连接创建总数统计：" + connectionPool.getCreatedCount() + "\r\n" +
                    "#\t 连接销毁总数统计：" + connectionPool.getDestroyedCount() + "\r\n" +
                    "#\t 连接销毁(因为连接不可用)总数统计：" + connectionPool.getDestroyedByBorrowValidationCount() + "\r\n" +
                    "#\t 连接销毁(因为连接被回收)总数统计：" + connectionPool.getDestroyedByEvictorCount() + "\r\n" +
                    "#=======================================================================================================================#\r\n";
            logger.debug(tmp);
        }
    }
    
    public void splitFileId(String fileId, String[] results) {
        int pos = fileId.indexOf(OtherConstants.SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR);
        if ((pos <= 0) || (pos == fileId.length() - 1)) {
            throw new IllegalArgumentException("file_id is invalid path!");
        }
        results[0] = fileId.substring(0, pos); // group name
        results[1] = fileId.substring(pos + 1); // file name
    }

    @BeforeTest
    public void init() {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(500, 500);
        GenericKeyedObjectPoolConfig conf = new GenericKeyedObjectPoolConfig();
        conf.setMaxTotal(200);
        conf.setMaxTotalPerKey(200);
        conf.setMaxIdlePerKey(100);
        connectionPool = new ConnectionPool(pooledConnectionFactory, conf);
        Set<String> trackerSet = new HashSet<String>();
        trackerSet.add("192.168.0.48:22122");
        DefaultCommandExecutor commandExecutor = new DefaultCommandExecutor(trackerSet, connectionPool);
        TrackerClient trackerClient = new DefaultTrackerClient(commandExecutor);
        storageClient = new DefaultStorageClient(commandExecutor, trackerClient);
    }

    @AfterTest
    public void close() {
        //destory
        for(String id : resultFileId) {
            boolean flag = storageClient.deleteFile(id);
            logger.info("#####destory:"+id+"===result:" + flag);
        }
        dumpPoolInfo();
        connectionPool.close();
    }
    
    @Test
    public void uploadFileTest1() throws IOException {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        File file = new File(path);
        FileInputStream fileInputStream = FileUtils.openInputStream(file);
        StorePath storePath = storageClient.uploadFile("group1", fileInputStream, file.length(), "txt");
        fileInputStream.close();
        logger.info("==========uploadFileTest1()######fullPath : " + storePath.getFullPath());
        addResultFileId(storePath.getFullPath());
    }
    
    @Test
    public void uploadFileTest2() throws IOException {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        File file = new File(path);
        FileInputStream fileInputStream = FileUtils.openInputStream(file);
        StorePath storePath = storageClient.uploadFile(fileInputStream, file.length(), "txt");
        fileInputStream.close();
        logger.info("==========uploadFileTest2()######fullPath : " + storePath.getFullPath());
        addResultFileId(storePath.getFullPath());
    }
    
    @Test
    public void uploadFileTest3() throws IOException {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        StorePath storePath = storageClient.uploadFile(path);
        logger.info("==========uploadFileTest3()######fullPath : " + storePath.getFullPath());
        addResultFileId(storePath.getFullPath());
    }
    
    @Test
    public void uploadFileTest4() throws IOException {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        StorePath storePath = storageClient.uploadFile(path, "txt");
        logger.info("==========uploadFileTest4()######fullPath : " + storePath.getFullPath());
        addResultFileId(storePath.getFullPath());
    }
    
    @Test
    public void uploadFileTest5() throws IOException {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        StorePath storePath = storageClient.uploadFile("group1", path, "txt");
        logger.info("==========uploadFileTest5()######fullPath : " + storePath.getFullPath());
        addResultFileId(storePath.getFullPath());
    }
    
    @Test
    public void uploadFileTest6() throws IOException {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        File file = new File(path);
        FileInputStream fileInputStream = FileUtils.openInputStream(file);
        Set<MateData> metaDataSet = new HashSet<MateData>();
        MateData mateData = new MateData("mateDataName","mateDataValue");
        metaDataSet.add(mateData);
        StorePath storePath = storageClient.uploadFile(fileInputStream, file.length(), "txt", metaDataSet);
        fileInputStream.close();
        logger.info("==========uploadFileTest6()######fullPath : " + storePath.getFullPath());
        addResultFileId(storePath.getFullPath());
    }
    
    @Test
    public void uploadFileTest7() throws IOException {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        File file = new File(path);
        FileInputStream fileInputStream = FileUtils.openInputStream(file);
        Set<MateData> metaDataSet = new HashSet<MateData>();
        MateData mateData = new MateData("mateDataName","mateDataValue");
        metaDataSet.add(mateData);
        StorePath storePath = storageClient.uploadFile("group1", fileInputStream, file.length(), "txt", metaDataSet);
        fileInputStream.close();
        logger.info("==========uploadFileTest7()######fullPath : " + storePath.getFullPath());
        addResultFileId(storePath.getFullPath());
    }
    
    @Test
    public void uploadSlaveFile1() throws IOException {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        StorePath storePath = storageClient.uploadFile(path, "txt");
        addResultFileId(storePath.getFullPath());
        
        path = ClassLoader.getSystemResource("123456slave.txt").getPath();
        File file = new File(path);
        FileInputStream fileInputStream = FileUtils.openInputStream(file);
        
        String masterFileId = storePath.getFullPath();
        String[] parts = new String[2];
        splitFileId(masterFileId, parts);
        
        storePath = storageClient.uploadSlaveFile(parts[0], parts[1], fileInputStream, file.length(), "-1", "txt");
        fileInputStream.close();
        logger.info("==========uploadSlaveFile1()######fullPath : " + storePath.getFullPath());
        addResultFileId(storePath.getFullPath());
    }
    
    @Test
    public void uploadSlaveFile2() throws IOException {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        StorePath storePath = storageClient.uploadFile(path, "txt");
        addResultFileId(storePath.getFullPath());
        
        path = ClassLoader.getSystemResource("test.xlsx").getPath();
        File file = new File(path);
        FileInputStream fileInputStream = FileUtils.openInputStream(file);
        
        String masterFileId = storePath.getFullPath();
        
        storePath = storageClient.uploadSlaveFile(masterFileId, fileInputStream, file.length(), "-1", "xlsx");
        fileInputStream.close();
        logger.info("==========uploadSlaveFile1()######fullPath : " + storePath.getFullPath());
        addResultFileId(storePath.getFullPath());
    }
    
    @Test
    public void getMetadataTest1() throws IOException {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        File file = new File(path);
        FileInputStream fileInputStream = FileUtils.openInputStream(file);
        Set<MateData> metaDataSet = new HashSet<MateData>();
        MateData mateData = new MateData("mateDataName","mateDataValue");
        metaDataSet.add(mateData);
        StorePath storePath = storageClient.uploadFile("group1", fileInputStream, file.length(), "txt", metaDataSet);
        fileInputStream.close();
        addResultFileId(storePath.getFullPath());
        
        String masterFileId = storePath.getFullPath();
        String[] parts = new String[2];
        splitFileId(masterFileId, parts);
        
        Set<MateData> mateDataSet = storageClient.getMetadata(parts[0], parts[1]);
        for (MateData m : mateDataSet) {
            logger.info("#####=====getMetadataTest1()=======" + mateData);
        }
    }
    
    @Test
    public void getMetadataTest2() throws IOException {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        File file = new File(path);
        FileInputStream fileInputStream = FileUtils.openInputStream(file);
        Set<MateData> metaDataSet = new HashSet<MateData>();
        MateData mateData = new MateData("mateDataName","mateDataValue");
        metaDataSet.add(mateData);
        StorePath storePath = storageClient.uploadFile("group1", fileInputStream, file.length(), "txt", metaDataSet);
        fileInputStream.close();
        addResultFileId(storePath.getFullPath());
        
        String masterFileId = storePath.getFullPath();
        
        Set<MateData> mateDataSet = storageClient.getMetadata(masterFileId);
        for (MateData m : mateDataSet) {
            logger.info("#####=====getMetadataTest2()=======" + mateData);
        }
    }
    
    @Test
    public void overwriteMetadataTest1() throws IOException {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        File file = new File(path);
        FileInputStream fileInputStream = FileUtils.openInputStream(file);
        Set<MateData> metaDataSet = new HashSet<MateData>();
        MateData mateData = new MateData("mateDataName","mateDataValue");
        metaDataSet.add(mateData);
        StorePath storePath = storageClient.uploadFile("group1", fileInputStream, file.length(), "txt", metaDataSet);
        fileInputStream.close();
        addResultFileId(storePath.getFullPath());
        String masterFileId = storePath.getFullPath();
        Set<MateData> mateDataSet = storageClient.getMetadata(masterFileId);
        logger.info("#####=====overwriteMetadataTest1()===修改前的元数据====" + mateDataSet);
        
        String[] parts = new String[2];
        splitFileId(masterFileId, parts);
        
        mateDataSet = new HashSet<MateData>();
        mateDataSet.add(new MateData("key5", "value5"));
        mateDataSet.add(new MateData("key6", "value6"));
        mateDataSet.add(new MateData("key7", "value7"));
        boolean flag = storageClient.overwriteMetadata(parts[0], parts[1], mateDataSet);
        logger.info("#####=====overwriteMetadataTest1()===修改元数据请求结果====" + flag);
        
        mateDataSet = storageClient.getMetadata(masterFileId);
        logger.info("#####=====overwriteMetadataTest1()===修改后的元数据====" + mateDataSet);
    }
    
    @Test
    public void overwriteMetadataTest2() throws IOException {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        File file = new File(path);
        FileInputStream fileInputStream = FileUtils.openInputStream(file);
        Set<MateData> metaDataSet = new HashSet<MateData>();
        MateData mateData = new MateData("mateDataName","mateDataValue");
        metaDataSet.add(mateData);
        StorePath storePath = storageClient.uploadFile("group1", fileInputStream, file.length(), "txt", metaDataSet);
        fileInputStream.close();
        addResultFileId(storePath.getFullPath());
        String masterFileId = storePath.getFullPath();
        Set<MateData> mateDataSet = storageClient.getMetadata(masterFileId);
        logger.info("#####=====overwriteMetadataTest2()===修改前的元数据====" + mateDataSet);
        
        mateDataSet = new HashSet<MateData>();
        mateDataSet.add(new MateData("key5", "value5"));
        mateDataSet.add(new MateData("key6", "value6"));
        mateDataSet.add(new MateData("key7", "value7"));
        boolean flag = storageClient.overwriteMetadata(masterFileId, mateDataSet);
        logger.info("#####=====overwriteMetadataTest2()===修改元数据请求结果====" + flag);
        
        mateDataSet = storageClient.getMetadata(masterFileId);
        logger.info("#####=====overwriteMetadataTest2()===修改后的元数据====" + mateDataSet);
    }
    
    @Test
    public void mergeMetadataTest1() throws IOException {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        File file = new File(path);
        FileInputStream fileInputStream = FileUtils.openInputStream(file);
        Set<MateData> metaDataSet = new HashSet<MateData>();
        MateData mateData = new MateData("mateDataName","mateDataValue");
        metaDataSet.add(mateData);
        StorePath storePath = storageClient.uploadFile("group1", fileInputStream, file.length(), "txt", metaDataSet);
        fileInputStream.close();
        addResultFileId(storePath.getFullPath());
        String masterFileId = storePath.getFullPath();
        Set<MateData> mateDataSet = storageClient.getMetadata(masterFileId);
        logger.info("#####=====mergeMetadataTest1()===合并前的元数据====" + mateDataSet);
        
        String[] parts = new String[2];
        splitFileId(masterFileId, parts);
        
        mateDataSet = new HashSet<MateData>();
        mateDataSet.add(new MateData("key5", "value5"));
        mateDataSet.add(new MateData("key6", "value6"));
        mateDataSet.add(new MateData("key7", "value7"));
        boolean flag = storageClient.mergeMetadata(parts[0], parts[1], mateDataSet);
        logger.info("#####=====mergeMetadataTest1()===合并元数据请求结果====" + flag);
        
        mateDataSet = storageClient.getMetadata(masterFileId);
        logger.info("#####=====mergeMetadataTest1()===合并后的元数据====" + mateDataSet);
    }
    
    @Test
    public void mergeMetadataTest2() throws IOException {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        File file = new File(path);
        FileInputStream fileInputStream = FileUtils.openInputStream(file);
        Set<MateData> metaDataSet = new HashSet<MateData>();
        MateData mateData = new MateData("mateDataName","mateDataValue");
        metaDataSet.add(mateData);
        StorePath storePath = storageClient.uploadFile("group1", fileInputStream, file.length(), "txt", metaDataSet);
        fileInputStream.close();
        addResultFileId(storePath.getFullPath());
        String masterFileId = storePath.getFullPath();
        Set<MateData> mateDataSet = storageClient.getMetadata(masterFileId);
        logger.info("#####=====mergeMetadataTest2()===合并前的元数据====" + mateDataSet);
        
        mateDataSet = new HashSet<MateData>();
        mateDataSet.add(new MateData("key5", "value5"));
        mateDataSet.add(new MateData("key6", "value6"));
        mateDataSet.add(new MateData("key7", "value7"));
        boolean flag = storageClient.mergeMetadata(masterFileId, mateDataSet);
        logger.info("#####=====mergeMetadataTest2()===合并元数据请求结果====" + flag);
        
        mateDataSet = storageClient.getMetadata(masterFileId);
        logger.info("#####=====mergeMetadataTest2()===合并后的元数据====" + mateDataSet);
    }
    
    @Test
    public void getFileInfoTest1() {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        StorePath storePath = storageClient.uploadFile("group1", path, "txt");
        addResultFileId(storePath.getFullPath());
        String fileId = storePath.getFullPath();
        FileInfo fileInfo = storageClient.getFileInfo(storePath.getGroup(), storePath.getPath());
        logger.info("#####=====getFileInfoTest1====" + fileInfo);
        fileInfo = storageClient.getFileInfo(fileId);
        logger.info("#####=====getFileInfoTest1====" + fileInfo);
    }
    
    @Test
    public void getFileInfoTest2() {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        StorePath storePath = storageClient.uploadFile("group1", path, "txt");
        addResultFileId(storePath.getFullPath());
        String fileId = storePath.getFullPath();
        FileInfo fileInfo = storageClient.getFileInfo(fileId);
        logger.info("#####=====getFileInfoTest2====" + fileInfo);
    }
    
    @Test
    public void deleteFileTest1() {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        StorePath storePath = storageClient.uploadFile("group1", path, "txt");
        boolean flag = storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        logger.info("#####=====deleteFileTest1====" + flag);
    }
    
    @Test
    public void deleteFileTest2() {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        StorePath storePath = storageClient.uploadFile("group1", path, "txt");
        boolean flag = storageClient.deleteFile(storePath.getFullPath());
        logger.info("#####=====deleteFileTest2====" + flag);
    }
    
    @Test
    public void downloadFileTest1() {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        StorePath storePath = storageClient.uploadFile("group1", path, "txt");
        addResultFileId(storePath.getFullPath());
        DownloadFileWriter downloadFileWriter = new DownloadFileWriter(path.replaceAll("123456.txt", "123456downlaod1.txt"));
        String filePath = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), downloadFileWriter);
        logger.info("#####=====downloadFileTest1====" + filePath);
    }
    
    @Test
    public void downloadFileTest2() {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        StorePath storePath = storageClient.uploadFile("group1", path, "txt");
        addResultFileId(storePath.getFullPath());
        DownloadFileWriter downloadFileWriter = new DownloadFileWriter(path.replaceAll("123456.txt", "123456downlaod2.txt"));
        String filePath = storageClient.downloadFile(storePath.getFullPath(), downloadFileWriter);
        logger.info("#####=====downloadFileTest2====" + filePath);
    }
    
    @Test
    public void downloadFileTest3() {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        StorePath storePath = storageClient.uploadFile("group1", path, "txt");
        addResultFileId(storePath.getFullPath());
        DownloadFileWriter downloadFileWriter = new DownloadFileWriter(path.replaceAll("123456.txt", "123456downlaod3.txt"));
        String filePath = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), 10, 0, downloadFileWriter);
        logger.info("#####=====downloadFileTest3====" + filePath);
    }
    
    @Test
    public void downloadFileTest4() {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        StorePath storePath = storageClient.uploadFile("group1", path, "txt");
        addResultFileId(storePath.getFullPath());
        DownloadFileWriter downloadFileWriter = new DownloadFileWriter(path.replaceAll("123456.txt", "123456downlaod4.txt"));
        String filePath = storageClient.downloadFile(storePath.getFullPath(), 5, 0, downloadFileWriter);
        logger.info("#####=====downloadFileTest4====" + filePath);
    }
    
    @Test
    public void appendFileTest1() throws IOException {
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        StorePath storePath = storageClient.uploadFile("group1", path, "txt");
        addResultFileId(storePath.getFullPath());
        
        File file = new File(path);
        FileInputStream fileInputStream = FileUtils.openInputStream(file);
        
        storePath = storageClient.uploadAppenderFile(storePath.getGroup(), fileInputStream, file.length(), "txt");
        fileInputStream.close();
        logger.info("#####=====appendFileTest1====" + storePath.getFullPath());
        addResultFileId(storePath.getFullPath());
    }
    
//    @Test
//    public void appendFileTest2() throws IOException {
//        String path = ClassLoader.getSystemResource("123456.txt").getPath();
//        StorePath storePath = storageClient.uploadFile("group1", path, "txt");
//        addResultFileId(storePath.getFullPath());
//        logger.info("#####=====appendFileTest2====" + storePath.getFullPath());
//        
//        File file = new File(path);
//        FileInputStream fileInputStream = FileUtils.openInputStream(file);
//        
//        storageClient.appendFile(storePath.getGroup(), storePath.getPath(), fileInputStream, file.length());
//        fileInputStream.close();
//    }
    
//    @Test
//    public void truncateFileTest1() throws IOException {
//        String path = ClassLoader.getSystemResource("123456.txt").getPath();
//        StorePath storePath = storageClient.uploadFile("group1", path, "txt");
//        addResultFileId(storePath.getFullPath());
//        logger.info("#####=====truncateFileTest1====" + storePath.getFullPath());
//        File file = new File(path);
//        FileInputStream fileInputStream = FileUtils.openInputStream(file);
//        storageClient.truncateFile("group1/M00/00/00/wKgAMFkdFtWAPRclAAAAF_BgSIY996.txt", 10);
//        fileInputStream.close();
//    }
    
    
//    @Test
//    public void modifyFileTest() throws IOException {
//        String path = ClassLoader.getSystemResource("123456.txt").getPath();
//        File file = new File(path);
//        FileInputStream fileInputStream = FileUtils.openInputStream(file);
////        String fildId = "group1/M00/00/00/wKgAMFkcFKmACkCJAAAAAAAAAAA017.txt";
////        storageClient.modifyFile(fildId, fileInputStream, file.length(), 0);
////        storageClient.modifyFile("group1", "M00/00/00/wKgAMFkcFKmACkCJAAAAAAAAAAA017.txt", fileInputStream, file.length(), 0);
//        storageClient.modifyFile("group1", "M00/00/00/wKgAMFkcGwKAJIADAAAAF_BgSIY503.txt", fileInputStream, file.length(), 0);
//        fileInputStream.close();
////        logger.info("#####===== " + fildId);
//    }

}
