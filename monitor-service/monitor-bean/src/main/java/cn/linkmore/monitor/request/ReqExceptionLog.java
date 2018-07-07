package cn.linkmore.monitor.request;

import java.util.Date;

public class ReqExceptionLog {

	  /**
     *  主键
     */ 
    private Long id;

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
