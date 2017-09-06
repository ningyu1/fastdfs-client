package cn.tsoft.framework.fastdfs.model;

import cn.tsoft.framework.fastdfs.constant.OtherConstants;
import cn.tsoft.framework.fastdfs.mapper.FastDFSColumn;

import java.io.Serializable;
import java.net.InetSocketAddress;

/**
 * 向tracker请求上传、下载文件或其他文件请求时，tracker返回的文件storage节点的信息
 * <p>
 * @author ningyu
 * @date 2017年5月17日 下午4:30:55
 */
public class StorageNode implements Serializable {
    /**
     */
    private static final long serialVersionUID = -4677030890236583826L;

    @FastDFSColumn(index = 0, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private String groupName;

    @FastDFSColumn(index = 1, max = OtherConstants.FDFS_IPADDR_SIZE - 1)
    private String ip;

    @FastDFSColumn(index = 2)
    private int port;

    @FastDFSColumn(index = 3)
    private byte storeIndex;

    /**
     * 存储节点
     *
     * @param ip         存储服务器IP地址
     * @param port       存储服务器端口号
     * @param storeIndex 存储服务器顺序
     */
    public StorageNode(String ip, int port, byte storeIndex) {
        super();
        this.ip = ip;
        this.port = port;
        this.storeIndex = storeIndex;
    }

    public StorageNode() {
        super();
    }

    /**
     * @return 根据IP和端口 返回 InetSocketAddress 对象
     */
    public InetSocketAddress getInetSocketAddress() {
        return new InetSocketAddress(ip, port);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public byte getStoreIndex() {
        return storeIndex;
    }

    public void setStoreIndex(byte storeIndex) {
        this.storeIndex = storeIndex;
    }

    @Override
    public String toString() {
        return "StorageNode{" +
                "groupName='" + groupName + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", storeIndex=" + storeIndex +
                '}';
    }
}
