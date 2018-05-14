package cn.linkmore.account.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.Common;
@Mapper
public interface CommonClusterMapper {
	
	public List<Map<String,Object>> selectList(Common common);

	public Integer count(Common common);


}