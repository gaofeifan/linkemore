package cn.linkmore.security.response;

import java.util.Date;
/**
 * 响应-字典分组
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResDictGroup {
	/**
	 * 主键
	 */
    private Long id;
    /**
     * 分组名称
     */
    private String name;
    /**
     * 分组编号
     */
    private String code;
    /**
     * 排序
     */
    private Integer orderIndex;
    /**
     * 创建时间
     */
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

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
