package cn.linkmore.bean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.linkmore.bean.ViewOrder.Direction;
 
/**
 * 通用 - 请求 - 分页封装
 * @author liwenlong
 * @version 1.0
 *
 */
public class ViewPageable implements Serializable { 
	/**
	 * serial id
	 */
	private static final long serialVersionUID = 9151079986318529198L;
	private static final int DEFAULT_START = 0; 
	private static final int DEFAULT_PAGE_SIZE = 20; 
	private static final int MAX_PAGE_SIZE = 1000; 
	private int start = DEFAULT_START; 
	private int pageSize = DEFAULT_PAGE_SIZE; 
	private String searchProperty; 
	private String searchValue; 
	private String orderProperty; 

	/** 排序方向 */
	private Direction orderDirection;
	
	/** 
	 * 筛选
	 **/
	private List<ViewFilter> filters = new ArrayList<ViewFilter>();

	/** 
	 * 排序
	 **/
	private List<ViewOrder> orders = new ArrayList<ViewOrder>();
	
	/**
	 * 查询条件
	 */
	protected String filterJson = null;
	
	/**
	 * 排序条件
	 */
	private String orderJson = null;
	
	public ViewPageable() {
		
	}
 
	public ViewPageable(Integer start, Integer pageSize) {
		if (start != null && start >= 0) {
			this.start = start;
		}
		if (pageSize != null && pageSize >= 1 && pageSize <= MAX_PAGE_SIZE) {
			this.pageSize = pageSize;
		}
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		if (start < 1) {
			start = DEFAULT_START;
		}
		this.start = start;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	public String getSearchProperty() {
		return searchProperty;
	}

	public void setSearchProperty(String searchProperty) {
		this.searchProperty = searchProperty;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getOrderProperty() {
		return orderProperty;
	}

	public void setOrderProperty(String orderProperty) {
		this.orderProperty = orderProperty;
	}

	public Direction getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(Direction orderDirection) {
		this.orderDirection = orderDirection;
	}

	public List<ViewFilter> getFilters() {
		return filters;
	}

	public void setFilters(List<ViewFilter> filters) {
		this.filters = filters;
	}

	public List<ViewOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<ViewOrder> orders) {
		this.orders = orders;
	}

	public String getFilterJson() {
		return filterJson;
	}

	public void setFilterJson(String filterJson) {
		try{
			ObjectMapper objectMapper =new ObjectMapper(); 
			this.setFilters(objectMapper.readValue(filterJson, Filters.class).getFilters());
		}catch(Exception e){
		} 
		this.filterJson = filterJson;
	}

	public String getOrderJson() {
		return orderJson;
	}

	public void setOrderJson(String orderJson) {
		try{
			ObjectMapper objectMapper =new ObjectMapper(); 
			this.setOrders(objectMapper.readValue(orderJson, OrderList.class).getOrderList());
		}catch(Exception e){
		}  
		this.orderJson = orderJson;
	}
	
}
class OrderList{
	List<ViewOrder> orderList;

	public List<ViewOrder> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<ViewOrder> orderList) {
		this.orderList = orderList;
	} 
}
class Filters { 
	List<ViewFilter> filters;

	public List<ViewFilter> getFilters() {
		return filters;
	}

	public void setFilters(List<ViewFilter> filters) {
		this.filters = filters;
	}
 
}