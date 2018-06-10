package io.github.ningyu.fastdfs.mapper;

/**
 * 动态属性类型<br/>
 * 可以为空的属性-不发送该报文<br/>
 * 剩余的所有byte-将该字段全部写入到最后的byte当中<br/>
 * <p>
 * @author ningyu
 * @date 2017年5月17日 下午4:29:20
 */
public enum DynamicFieldType {
    /**
     * 非动态属性
     */
    NULL,

    /**
     * 剩余的所有Byte
     */
    allRestByte,

    /**
     * 可空的属性
     */
    nullable,

    /**
     * 文件元数据Set
     */
    matedata
}
