package cn.linkmore.security.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.RoleElement;
/**
 * 角色元素
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface RoleElementMasterMapper {
    int delete(Long id);

    int save(RoleElement record);

    int update(RoleElement record);

	void batchSave(List<RoleElement> res);
}