package cn.linkmore.order.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.CompanyTradeRecord;
@Mapper
public interface CompanyTradeRecordClusterMapper {

    CompanyTradeRecord findById(Integer id);

    /**
     * 最近
     * @return
     */
	CompanyTradeRecord findLast();

}