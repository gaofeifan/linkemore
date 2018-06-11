package cn.linkmore.account.entity;

/**
 * @author   GFF
 * @Date     2018年6月9日
 * @Version  v2.0
 */
public class NoticeContent {
    private Long id;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}