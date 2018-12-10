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
	void deleteByIds(List<Long> ids);

    int save(RentEntStall record);

    int update(RentEntStall record);

	void saveBatch(List<ReqRentEntStall> list);
}