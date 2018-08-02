package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntVipUser;

/**
 * 企业vip用户---读
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntVipUserClusterMapper {

    EntVipUser findById(Long id);

	List<EntVipUser> findByIdEntPreId(Long entPreId);
	
	
	Integer count(Map<String, Object> param);


	List<EntVipUser> findPage(Map<String, Object> param);
	
	
	
}