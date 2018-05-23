package cn.linkmore.security.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.Menu;
/**
 * 菜单
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface MenuClusterMapper {

    Menu findById(Long id);

	List<Menu> findTree();

	Integer count(Map<String, Object> param);

	List<Menu> findPage(Map<String, Object> param);

	Integer check(Map<String, Object> param);

	List<Menu> findPersonAuthList(Long id);

}