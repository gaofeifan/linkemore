package cn.linkmore.account.request;

/**
 * 消息分页请求bean
 * @author   GFF
 * @Date     2018年6月9日
 * @Version  v2.0
 */
public class ReqPageNotice {
	private static final int DEFAULT_PAGE = 0;
	private static final int DEFAULT_SIZE = 10;

	/**
	 * 当前页，默认0
	 */
	private int page = DEFAULT_PAGE;

	/**
	 * 每页多少条，默认10条
	 */
	private int pageSize = DEFAULT_SIZE;

	private Long userId;
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
