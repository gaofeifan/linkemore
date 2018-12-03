package cn.linkmore.common.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.request.ReqPayConfig;
import cn.linkmore.common.response.ResPayConfig;

@Mapper
public interface PayConfigClusterMapper {

	ResPayConfig getConfig(ReqPayConfig reqPayConfig);
	
}
