package cn.tsoft.framework.fastdfs.constant;

/**
 * FastDFS协议服务端状态的常量
 * @author ningyu
 * @date 2017年5月17日 下午4:22:03
 */
public final class StatusConstants {
    public static final byte FDFS_STORAGE_STATUS_INIT = 0;
    public static final byte FDFS_STORAGE_STATUS_WAIT_SYNC = 1;
    public static final byte FDFS_STORAGE_STATUS_SYNCING = 2;
    public static final byte FDFS_STORAGE_STATUS_IP_CHANGED = 3;
    public static final byte FDFS_STORAGE_STATUS_DELETED = 4;
    public static final byte FDFS_STORAGE_STATUS_OFFLINE = 5;
    public static final byte FDFS_STORAGE_STATUS_ONLINE = 6;
    public static final byte FDFS_STORAGE_STATUS_ACTIVE = 7;
    public static final byte FDFS_STORAGE_STATUS_NONE = 99;
}
