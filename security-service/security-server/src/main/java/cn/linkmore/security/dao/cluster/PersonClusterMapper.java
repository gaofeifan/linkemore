package cn.linkmore.security.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.response.ResPerson;
/**
 * 账户
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface PersonClusterMapper {

	ResPerson findById(Long id);

	ResPerson findByUsername(String username);

	Integer count(Map<String, Object> param);

	List<ResPerson> findPage(Map<String, Object> param);

	Integer check(Map<String, Object> param);

}