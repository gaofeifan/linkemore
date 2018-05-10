package cn.linkmore.bean.view;

import java.io.Serializable; 
import java.util.List;

/**
 * 通用类 -响应- 分页封闭 
 * @author liwenlong
 * @version 1.0
 *
 */
public class ViewPage  implements Serializable {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 5044181027667758397L;
	/**
	 * 总记录娄
	 */
	private Integer total;
	/**
	 * 总分页数
	 */
	private Integer totalPages;
	/**
	 * 记录列表
	 */
	private List<?> list ; 
	
	public ViewPage(Integer total,Integer pageSize,List<?> list) {
		this.list = list;
		this.total = total;
		this.totalPages = (total-1)/pageSize + 1;
	}
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}  
}
