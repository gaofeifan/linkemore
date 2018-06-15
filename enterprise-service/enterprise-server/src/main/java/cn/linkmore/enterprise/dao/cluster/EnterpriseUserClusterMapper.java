package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.enterprise.response.ResEnterpriseUser;
/**
 * 企业用户
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface EnterpriseUserClusterMapper {

    ResEnterpriseUser findById(Long id);
    
    List<ResEnterpriseUser> findPage(Map<String, Object> param);

	int count(Map<String, Object> param);

	ResEnterpriseUser getEntUserByUserId(Map<String, Object> param);

	List<ResEnterpriseUser> findList(Map<String, Object> param);

}