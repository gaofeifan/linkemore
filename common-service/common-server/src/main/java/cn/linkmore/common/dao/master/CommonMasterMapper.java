package cn.linkmore.common.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.Common;
@Mapper
public interface CommonMasterMapper {
	
	public void updateColumnValue(Common common);

	public void updateList(Common common);
	
	public void insertList(Common common);

	public void insert(Common common);

}