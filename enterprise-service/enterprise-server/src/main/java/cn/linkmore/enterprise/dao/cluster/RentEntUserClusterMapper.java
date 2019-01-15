package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.RentEntUser;
import cn.linkmore.enterprise.request.ReqRentEntUser;
/**
 * 企业长租用户
 * @author   GFF
 * @Date     2018年11月26日
 * @Version  v2.0
 */
@Mapper
public interface RentEntUserClusterMapper {

	RentEntUser findById(Long id);
	
	Integer count(Map<String, Object> param);

	List<RentEntUser> findPage(Map<String, Object> param);

	List<RentEntUser> findList(Map<String, Object> param);
	
	RentEntUser findMaxId();
	
	RentEntUser findByPlate(ReqRentEntUser ent);
}