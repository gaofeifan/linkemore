package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.RentEntStall;
/**
 * 企业长租车位
 * @author   GFF
 * @Date     2018年11月26日
 * @Version  v2.0
 */
@Mapper
public interface RentEntStallClusterMapper {

    RentEntStall findById(Long id);

	Integer count(Map<String, Object> param);

	List<RentEntStall> findPage(Map<String, Object> param);

	List<RentEntStall> stallListCompany(Long companyId);
}