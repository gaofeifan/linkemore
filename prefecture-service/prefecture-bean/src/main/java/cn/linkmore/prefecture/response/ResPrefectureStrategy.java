package cn.linkmore.prefecture.response;
/**
 * 响应-APP计费策略详情
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResPrefectureStrategy {
	/**
	 * 免费时长
	 */
	private String freeMins;

	/**
	 * 计费内容描述
	 */
	private String content;
	
	public String getFreeMins() {
		return freeMins;
	}

	public void setFreeMins(String freeMins) {
		this.freeMins = freeMins;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
