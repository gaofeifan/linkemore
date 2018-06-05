package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.Common;
/**
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface CommonMasterMapper {
	
	public void updateColumnValue(Common common);

	public void updateList(Common common);
	
	public void insertList(Common common);

	public void insert(Common common);

}