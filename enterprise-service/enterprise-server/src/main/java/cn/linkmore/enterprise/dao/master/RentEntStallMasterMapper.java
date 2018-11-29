package cn.linkmore.enterprise.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.RentEntStall;
import cn.linkmore.enterprise.request.ReqRentEntStall;
/**
 * 企业长租车位
 * @author   GFF
 * @Date     2018年11月26日
 * @Version  v2.0
 */
@Mapper
public interface RentEntStallMasterMapper {
    int deleteByPrimaryKey(Long rentEntId);

    int insert(RentEntStall record);

    int insertSelective(RentEntStall record);

    int updateByPrimaryKeySelective(RentEntStall record);

    int updateByPrimaryKey(RentEntStall record);

	void saveBatch(List<ReqRentEntStall> list);
}