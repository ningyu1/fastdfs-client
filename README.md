# fastdfs-client是什么

fastdfs-client是一个访问fastdfs的Java客户端框架，帮助开发人员快速使用分布式文件系统的工具，封装了TrackerClient操作来管理存储节点，封装了StorageClient操作来执行文件上传下载功能。

具体查看：https://ningyu1.github.io/site/post/21-fastfds-client/

## 项目地址
[fastdfs-client](https://github.com/ningyu1/fastdfs-client) 

<a href="https://github.com/ningyu1/fastdfs-client/releases"><img src="https://img.shields.io/github/release/ningyu1/fastdfs-client.svg?style=social&amp;label=Release"></a>&nbsp;<a href="https://github.com/ningyu1/fastdfs-client/stargazers"><img src="https://img.shields.io/github/stars/ningyu1/fastdfs-client.svg?style=social&amp;label=Star"></a>&nbsp;<a href="https://github.com/ningyu1/fastdfs-client/fork"><img src="https://img.shields.io/github/forks/ningyu1/fastdfs-client.svg?style=social&amp;label=Fork"></a>&nbsp;<a href="https://github.com/ningyu1/fastdfs-client/watchers"><img src="https://img.shields.io/github/watchers/ningyu1/fastdfs-client.svg?style=social&amp;label=Watch"></a> <a href="http://www.gnu.org/licenses/gpl-3.0.html"><img src="https://img.shields.io/badge/license-GPLv3-blue.svg"></a>

## change log

[V1.1.1](https://github.com/ningyu1/fastdfs-client/releases/tag/V1.1.1)

1. 去掉pom中parent的依赖
2. 重构包名

[V1.1.0](https://github.com/ningyu1/fastdfs-client/releases/tag/V1.1.0)

1. 修改download文件receive时带入的inputStream对象，inputStream对象修改为克隆socket的inputstream，避免污染连接池中的socket对象，当业务回调不读取留时会影响下一次连接池中获取的socket对象。
2. 在使用1.0.0版本进行download文件时，建议使用DownloadCallback的实现类：DownloadByteArray和DownloadFileWriter不要自己去实现，不要关闭receive方法传入的inputStream对象。
3. 在使用1.1.0版本进行download文件时，receive传入的inputStream是克隆的，因此使用完后必须进行关闭操作。

[V1.0.0](https://github.com/ningyu1/fastdfs-client/releases/tag/V1.0.0)

1. 包装Request和Response报文解析
2. 包装Storage和Tracker操作命令
3. 增加连接池提升使用性能

## 接口方法

StorageClient

![](https://ningyu1.github.io/site/img/fastdfs-client/1.png)

TrackerClient

![](https://ningyu1.github.io/site/img/fastdfs-client/2.png)

## Maven引入

```xml
<dependency>
  <groupId>cn.tsoft.framework</groupId>
  <artifactId>fastdfs-client</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## Spring引入

```xml
<import resource="classpath:spring-fastdfs.xml"/>
```

## Client使用

```java
@Autowired
private StorageClient storageClient;
 
//上传
String path = ClassLoader.getSystemResource("123456.txt").getPath();
File file = new File(path);
FileInputStream fileInputStream = FileUtils.openInputStream(file);
//方式1
StorePath storePath = storageClient.uploadFile("group1", fileInputStream, file.length(), "txt");
//方式2
StorePath storePath = storageClient.uploadFile(fileInputStream, file.length(), "txt");
//方式3
StorePath storePath = storageClient.uploadFile(path);
//方式4
StorePath storePath = storageClient.uploadFile(path, "txt");
//方式5
StorePath storePath = storageClient.uploadFile("group1", path, "txt");
//方式6
StorePath storePath = storageClient.uploadFile(fileInputStream, file.length(), "txt", metaDataSet);
//上传文件并增加元数据
Set<MateData> metaDataSet = new HashSet<MateData>();
MateData mateData = new MateData("mateDataName","mateDataValue");
metaDataSet.add(mateData);
StorePath storePath = storageClient.uploadFile("group1", fileInputStream, file.length(), "txt", metaDataSet);
 
//上传从文件，一个主文件可以挂多个从文件
//方式1
String masterFileId = storePath.getFullPath();
String[] parts = new String[2];
splitFileId(masterFileId, parts);
storePath = storageClient.uploadSlaveFile(parts[0], parts[1], fileInputStream, file.length(), "-1", "txt");
//方式2
storePath = storageClient.uploadSlaveFile(masterFileId, fileInputStream, file.length(), "-1", "xlsx");
         
fileInputStream.close();
 
 
//下载
//方式1
String path = ClassLoader.getSystemResource("123456.txt").getPath();
StorePath storePath = storageClient.uploadFile("group1", path, "txt");
addResultFileId(storePath.getFullPath());
DownloadFileWriter downloadFileWriter = new DownloadFileWriter(path.replaceAll("123456.txt", "123456downlaod1.txt"));
String filePath = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), downloadFileWriter);
//方式2
DownloadFileWriter downloadFileWriter = new DownloadFileWriter(path.replaceAll("123456.txt", "123456downlaod2.txt"));
String filePath = storageClient.downloadFile(storePath.getFullPath(), downloadFileWriter);
//方式3
DownloadFileWriter downloadFileWriter = new DownloadFileWriter(path.replaceAll("123456.txt", "123456downlaod3.txt"));
String filePath = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), 10, 0, downloadFileWriter); 
//方式4
DownloadFileWriter downloadFileWriter = new DownloadFileWriter(path.replaceAll("123456.txt", "123456downlaod4.txt"));
String filePath = storageClient.downloadFile(storePath.getFullPath(), 5, 0, downloadFileWriter);
 
//删除
//方式1
String path = ClassLoader.getSystemResource("123456.txt").getPath();
StorePath storePath = storageClient.uploadFile("group1", path, "txt");
boolean flag = storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
//方式2
boolean flag = storageClient.deleteFile(storePath.getFullPath());
 
//获取文件信息
//方式1
String path = ClassLoader.getSystemResource("123456.txt").getPath();
StorePath storePath = storageClient.uploadFile("group1", path, "txt");
addResultFileId(storePath.getFullPath());
String fileId = storePath.getFullPath();
FileInfo fileInfo = storageClient.getFileInfo(storePath.getGroup(), storePath.getPath());
//方式2
FileInfo fileInfo = storageClient.getFileInfo(fileId);
 
//获取文件元数据
//方式1
String masterFileId = storePath.getFullPath();
String[] parts = new String[2];
splitFileId(masterFileId, parts);
Set<MateData> mateDataSet = storageClient.getMetadata(parts[0], parts[1]);
//方式2
Set<MateData> mateDataSet = storageClient.getMetadata(masterFileId);
 
//覆盖文件元数据
//方式1
String[] parts = new String[2];
splitFileId(masterFileId, parts);
mateDataSet = new HashSet<MateData>();
mateDataSet.add(new MateData("key5", "value5"));
mateDataSet.add(new MateData("key6", "value6"));
mateDataSet.add(new MateData("key7", "value7"));
boolean flag = storageClient.overwriteMetadata(parts[0], parts[1], mateDataSet);
//方式2
boolean flag = storageClient.overwriteMetadata(masterFileId, mateDataSet);
 
//合并文件元数据
//方式1
String[] parts = new String[2];
splitFileId(masterFileId, parts);
mateDataSet = new HashSet<MateData>();
mateDataSet.add(new MateData("key5", "value5"));
mateDataSet.add(new MateData("key6", "value6"));
mateDataSet.add(new MateData("key7", "value7"));
boolean flag = storageClient.mergeMetadata(parts[0], parts[1], mateDataSet);
//方式2
boolean flag = storageClient.mergeMetadata(masterFileId, mateDataSet);
 
//一下方法就不具体介绍
//续传文件
appendFile
//修改续传文件
modifyFile
//清除续传文件
truncateFile
```

**ps.TrackerClient的操作是配合StorageClient使用，我们在正常业务使用中一般不会用到它。**

## FastDFS-nginx-module使用

上传的文件可以通过nginx直接访问

例如：我们上传的文件获取的文件id：group1/M00/02/92/wKgAMFkekciAC8fhAAJjfD2dq-w10.xlsx

nginx访问路径：http://192.168.0.48:8079/group1/M00/02/92/wKgAMFkekciAC8fhAAJjfD2dq-w10.xlsx

目前nginx模块跟storage存储节点匹配，nginx会通过fastdfs-plugin跟tracker通信将文件的信息路由到不同的storage上去

## 注意事项

1. 上传文件后记录fileId，fastdfs不会自动删除文件，所以业务需要进行定期删除无用的文件，避免硬盘消耗过大
2. rpc之间调用时
	* 以前是rpc client端通过文件byte方式传入rpc server端，这样rpc的请求包过大会导致rpc调用性能急速下降
	* 应修改为通过fastdfs做桥接，rpc client端upload文件到fastdfs，将返回的fileId做参数传入rpc server端 ，rpc server端通过fileid去fastdfs服务器上download文件文件


## FastDFS-client-api说明

```java
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
```

**ps.api我只放出了StorageClient的说明，TrackerClient的使用常规开发时用不到的，架构在进行调优的时候可能会使用到，所以这里就不做过多的解释**
