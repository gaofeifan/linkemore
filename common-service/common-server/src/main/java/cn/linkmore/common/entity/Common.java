package cn.linkmore.common.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.linkmore.annotation.GColumn;
import cn.linkmore.annotation.GTable;
import cn.linkmore.bean.view.ViewOrder.Direction;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
/**
 * @Description  通用方法基本类
 * @author  GFF
 * @Date     2018年5月7日
 *
 */
public class Common {
	
	private String table;
	
	private String column;
	
	private String columns;
	
	private Object columnValue;
	
	private String sql;
	
	private String orderBy;
	
	private CreateCriteria criteria;
	
	private Class<?> clazz;
	
	private boolean isLimit;
	
	private String insertSql;
	
	private int pageStart;
	
	private int pageSize;
	
	private String updateSql;
	
	private List<String> insertSqls = new ArrayList<>();
	
	public Common(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getTable() {
		if(StringUtils.isBlank(table)){
			if(!clazz.isAnnotationPresent(GTable.class)){
				throw new RuntimeException("not found "+clazz+"@GTable annotation");
			}
			GTable gTable = clazz.getAnnotation(GTable.class);
			table =gTable.vlaue().toString();
		}
		return table;
	}

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
		if(StringUtils.isBlank(sql)){
			if(criteria!= null){
				sql = criteria.getSql();
			}
		}
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getColumns() {
		if(StringUtils.isBlank(columns)){
			StringBuilder sb = new StringBuilder();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if(field.isAnnotationPresent(GColumn.class)){
					GColumn c = field.getAnnotation(GColumn.class);
					if(c.isTableColumn()){
						if(StringUtils.isBlank(c.vlaue())){
							sb.append(DomainUtil.camelToUnderline(field.getName())).append(",");
						}else{
							sb.append(c.vlaue()).append(",");
						}
					}
				}
			}
			return deleteSplit(sb);
		}
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void addOrderBy(String properts ,Direction direction ) {
		this.orderBy = properts + " "+ direction.name() ;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public CreateCriteria createCriteria(){
		criteria = new CreateCriteria();
		return criteria;
	}
	
	public void addLimit(int start,int pageSize){
		isLimit = true;
		this.pageStart = start;
		this.pageSize = pageSize;
	}


	public void setTable(String table) {
		this.table = table;
	}

	public int getPageStart() {
		return pageStart;
	}
	

	public boolean getIsLimit() {
		return isLimit;
	}

	
	public int getPageSize() {
		return pageSize;
	}

	
	public <T> List<String> addInsertList(List<T> list){
		for (T t : list) {
			insertSqls.add(addInsert(t));
		}
		return insertSqls;
	}
	
	public <T> String addInsert(T t){
		Field[] fields = clazz.getDeclaredFields();
		StringBuilder values = new StringBuilder();
		try {
			for (Field field : fields) {
				if(field.isAnnotationPresent(GColumn.class)){
					GColumn c = field.getAnnotation(GColumn.class);
					if(c.isTableColumn()){
						field.setAccessible(true);
						if(c.isTableColumn()){
							Object object = field.get(t);
							if(object == null){
								values.append("null");
							}else{
								Object value = ObjectUtils.columnSetValue(field, object);
								values.append("'" + value + "'");
							}
							values.append(",");
						}
					}
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		String string = deleteSplit(values);
		insertSql = string;
		return string;
		
	}
	

	private String deleteSplit(StringBuilder sb){
		return sb.length() > 1 ? sb.substring(0, sb.length() - 1) : null;
	}

	public String getInsertSql() {
		return insertSql;
	}

	public List<String> getInsertSqls() {
		return insertSqls;
	}

	public String getUpdateSql() {
		return updateSql;
	}

	public void setUpdateSql(String updateSql) {
		this.updateSql = updateSql;
	}

	
	
	
}
