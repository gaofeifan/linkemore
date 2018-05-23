package cn.linkmore.security.entity;

import java.util.Date;
/**
 * 日志
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class Log {
	/**
	 * 主键
	 */
    private Long id;
    /**
	 * 用户名称
	 */
    private String personName;
    /**
	 * 用户id
	 */
    private Long personId;
    /**
     * 用户ip
     */
    private String personIp;
    /**
	 * 接口id
	 */
    private Long interfaceId;
    /**
	 * 接口名称
	 */
    private String interfaceName;
    /**
	 * 接口路径
	 */
    private String interfacePath;
    /**
	 * 描述
	 */
    private String description;
    /**
	 * 参数
	 */
    private String parameter;
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

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName == null ? null : personName.trim();
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonIp() {
        return personIp;
    }

    public void setPersonIp(String personIp) {
        this.personIp = personIp == null ? null : personIp.trim();
    }

    public Long getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName == null ? null : interfaceName.trim();
    }

    public String getInterfacePath() {
        return interfacePath;
    }

    public void setInterfacePath(String interfacePath) {
        this.interfacePath = interfacePath == null ? null : interfacePath.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}