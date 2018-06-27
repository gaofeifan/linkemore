package cn.linkmore.account.response;

import java.util.List;
public class ResPage<T> {
	private static final long serialVersionUID = 6887389993060457824L;
	/**
	 * 返回数据内容
	 */
	private List<T> rows;
	/**
	 * 总条目数
	 */
	private long records;

	private boolean status;

	public ResPage() {
	}

	public ResPage(List<T> rows, long records) {
		super();
		this.rows = rows;
		this.records = records;
	}


	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
}
