package cn.linkmore.security.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.security.response.ResMenu;
/**
 * 菜单
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface MenuClusterMapper {

	ResMenu findById(Long id);

	List<ResMenu> findTree();

	Integer count(Map<String, Object> param);

	List<ResMenu> findPage(Map<String, Object> param);

	Integer check(Map<String, Object> param);

	List<ResMenu> findPersonAuthList(Long id);

}