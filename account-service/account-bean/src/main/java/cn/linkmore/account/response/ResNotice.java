package cn.linkmore.account.response;

import java.util.Date;

/**
 * 响应消息的bean
 * @author   GFF
 * @Date     2018年6月9日
 * @Version  v2.0
 */
public class ResNotice {

	  private static final Long READ_STATUS = 0l; //默认未阅读

	    private Long count;

	    private String content;

	    public Long getCount() {
	        return count;
	    }

	    public void setCount(Long count) {
	        this.count = count;
	    }

	    public String getContent() {
	        return content;
	    }

	    public void setContent(String content) {
	        this.content = content;
	    }
}
