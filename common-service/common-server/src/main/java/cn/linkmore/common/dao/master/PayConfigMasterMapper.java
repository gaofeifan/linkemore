package cn.linkmore.common.dao.master;


import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.request.ReqPayRecord;


@Mapper
public interface PayConfigMasterMapper {

	int setOrderRecord(ReqPayRecord reqPayRecord);
	
}
