package cn.linkmore.third.request;

import cn.linkmore.bean.constant.PushType;
/**
 * 请求 - 推送
 * @author liwenlong
 * @version 2.0
 *
 */
public class ReqPush { 
	private String alias;
	private String title;
	private String content;
	private Short os;
	private PushType type;
	private String data;
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Short getOs() {
		return os;
	}
	public void setOs(Short os) {
		this.os = os;
	}
	public PushType getType() {
		return type;
	}
	public void setType(PushType type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	} 
	
}
