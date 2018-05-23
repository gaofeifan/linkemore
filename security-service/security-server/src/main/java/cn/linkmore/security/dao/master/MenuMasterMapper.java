package cn.linkmore.security.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.Menu;
/**
 * 菜单
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface MenuMasterMapper {

    int save(Menu record);

    int update(Menu record);

	int delete(List<Long> ids);
}