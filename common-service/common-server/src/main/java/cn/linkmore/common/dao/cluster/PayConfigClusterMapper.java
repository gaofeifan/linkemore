package cn.linkmore.common.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.request.ReqFinshOrder;
import cn.linkmore.common.request.ReqPayConfig;
import cn.linkmore.common.response.ResFinshOrder;
import cn.linkmore.common.response.ResPayConfig;

@Mapper
public interface PayConfigClusterMapper {

	ResPayConfig getConfig(ReqPayConfig reqPayConfig);
	
	List<ResFinshOrder> getOrder(ReqFinshOrder reqFinshOrder);
	
}
