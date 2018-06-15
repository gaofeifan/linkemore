package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.enterprise.response.ResEnterpriseDeal;
/**
 * 企业合同
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface EnterpriseDealClusterMapper {

    ResEnterpriseDeal findById(Long id);
	
	Integer check(Map<String, Object> param);
	
	Integer count(Map<String, Object> param);

	List<ResEnterpriseDeal> findPage(Map<String, Object> param);
	
	List<ResEnterpriseDeal> listByEnterpriseId(Integer enterpriseId, Integer isCreate);

	ResEnterpriseDeal selectByDealNumber(String number);
	
	List<ResEnterpriseDeal> selectByDealNumbers(List<String> dealNumberList);
}