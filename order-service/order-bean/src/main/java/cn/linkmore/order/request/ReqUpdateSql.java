package cn.linkmore.order.request;

/**
 * 更新sql
 * @author   GFF
 * @Date     2018年6月6日
 * @Version  v2.0
 */
public class ReqUpdateSql {
	
	private String column;
	
	private Object columnValue;

	private String sql;

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}


	public Object getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(Object columnValue) {
		this.columnValue = columnValue;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

}
