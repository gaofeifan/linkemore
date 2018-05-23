package cn.linkmore.common.entity;

/**
 * app异常日志内容
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
public class UnusualLogContent {
    /**
     *  异常日志id
     */ 
    private Long logId;

    /**
     *  内容
     */ 
    private String content;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}