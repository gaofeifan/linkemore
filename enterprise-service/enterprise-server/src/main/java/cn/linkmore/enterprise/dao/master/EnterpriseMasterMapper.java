package cn.linkmore.enterprise.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.enterprise.entity.Enterprise;
@Mapper
/**
 * 企业
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface EnterpriseMasterMapper {
    int delete(Long id);

    int save(Enterprise record);

    int update(Enterprise record);
}