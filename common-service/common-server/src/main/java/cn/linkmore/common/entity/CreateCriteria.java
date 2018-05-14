package cn.linkmore.common.entity;

import java.util.Iterator;

public class CreateCriteria {

	protected static final String EQUALS = " = ";
	protected static final String IN = " in ";
	protected static final String LIKE = " like ";
	protected static final String le = " <= ";
	protected static final String ge = " >= ";
	protected static final String gt = " > ";
	protected static final String lt = " < ";

	private StringBuilder sb ;

	public CreateCriteria() {
		if(sb == null){
			sb = new StringBuilder();
		}
	}
	
	public CreateCriteria(StringBuilder sb) {
		if(sb == null){
			sb = new StringBuilder();
		}else{
			this.sb = sb;
		}
	}

	public CreateCriteria equals(String colunm,Object value ){
		return addJoin(colunm, EQUALS, value);
	}
	
	public CreateCriteria addCondition(String colunm,Object value ){
		return addJoinCondition(colunm+value);
	}
	
	public CreateCriteria addCondition(String colunm){
		return addJoinCondition(colunm);
	}
	
	public CreateCriteria in(String colunm,Iterator<?> value ){
		if(value != null){
			StringBuilder s = new StringBuilder();
			while(value.hasNext()){
				s.append(value.next()).append(",");
			}
			String string = s.substring(0, s.length()-1);
			return addJoin(colunm, IN, "("+string+")");
		}
		return addJoin(colunm, IN, value);
	}
	
	public CreateCriteria like(String colunm,Object value ){
		if(value != null  && !value.toString().equals(" ")){
			return addJoin(colunm, LIKE, "'%"+value.toString()+"%'");
		}
		return addJoin(colunm, LIKE, value);
	}

	public CreateCriteria likeAfter(String colunm,Object value ){
		if(value != null && !value.toString().equals(" ")){
			return addJoin(colunm, LIKE, "'"+value.toString()+"%'");
		}
		return addJoin(colunm, LIKE, value);
	}
	/**
	 * 小于等于
	 * @Description  
	 * @Author   GFF 
	 */
	public CreateCriteria le(String colunm,Object value ){
		return addIs(colunm, le, value);
	}
	
	/**
	 * 大于等于
	 * @Description  
	 * @Author   GFF 
	 */
	public CreateCriteria ge(String colunm,Object value ){
		return addIs(colunm, ge, value);
	}
	
	/**
	 * 大于
	 * @Description  
	 * @Author   GFF 
	 */
	public CreateCriteria gt(String colunm,Object value ){
		return addIs(colunm, gt, value);
	}
	
	/**
	 * 小于
	 * @Description  
	 */
	public CreateCriteria lt(String colunm,Object value ){
		return addIs(colunm, lt, value);
	}

	private CreateCriteria addIs(String colunm,String str,Object value ){
		if(value != null){
			return addJoin(colunm,str,"'"+value+"'");
		}
		return addJoin(colunm,str,value);
		
//		return addJoin(colunm," <![CDATA["+str+"]]> ",value);
	}
	
	private CreateCriteria addJoin(String colunm,String str,Object value ){
		if(value != null  && !value.toString().equals(" ")){
			sb.append(" AND ").append(colunm).append(str).append(value);
		}
		return new CreateCriteria(sb);
	}
	private CreateCriteria addJoinCondition(String colunm){
		sb.append(" AND ").append(colunm);
		return new CreateCriteria(sb);
	}

	public String getSql() {
		return sb.toString();
	}

}
