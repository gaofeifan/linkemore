package cn.linkmore.monitor.entity;

import java.util.Date;

public class ExceptionLog {
    /**
     *  主键
     */ 
    private Long id;

    /**
     *  级别
     */ 
    private String level;

    /**
     *  创建时间
     */ 
    private Date createTime;

    /**
     *  内容
     */ 
    private String content;

    /**
     *  
     */ 
    private String parameter;

    /**
     *  
     */ 
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}