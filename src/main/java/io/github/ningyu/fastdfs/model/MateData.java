package io.github.ningyu.fastdfs.model;

import java.io.Serializable;

/**
 * 文件元数据(MateData)
 * <p>
 * @author ningyu
 * @date 2017年5月17日 下午4:30:37
 */
public class MateData implements Serializable {

    /**
     */
    private static final long serialVersionUID = -4439338122871245456L;

    private String name;

    private String value;

    public MateData() {
    }

    public MateData(String name) {
        this.name = name;
    }

    public MateData(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MateData other = (MateData) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "NameValuePair [name=" + name + ", value=" + value + "]";
    }
}
