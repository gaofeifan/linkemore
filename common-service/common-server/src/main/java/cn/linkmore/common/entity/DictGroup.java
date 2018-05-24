package cn.linkmore.common.entity;

import java.util.Date;

/**
 * 数据字典分组(老)
 * @author   GFF
 * @Date     2018年5月24日
 * @Version  v2.0
 */
public class DictGroup {
    private Long id;

    private String name;

    private String code;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}