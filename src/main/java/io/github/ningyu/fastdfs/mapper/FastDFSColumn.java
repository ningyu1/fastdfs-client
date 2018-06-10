package io.github.ningyu.fastdfs.mapper;


import java.lang.annotation.*;

/**
 * 传输参数定义标签
 * @author ningyu
 * @date 2017年5月17日 下午4:29:29
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FastDFSColumn {
    /**
     * 映射顺序(从0开始)
     */
    int index() default 0;

    /**
     * String最长度
     */
    int max() default 0;

    /**
     * 动态属性
     */
    DynamicFieldType dynamicField() default DynamicFieldType.NULL;
}
