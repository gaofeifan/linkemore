package cn.linkmore.security.dao.cluster;

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

}