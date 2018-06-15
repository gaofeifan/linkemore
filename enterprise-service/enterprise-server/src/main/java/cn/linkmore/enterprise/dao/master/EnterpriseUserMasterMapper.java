package cn.linkmore.enterprise.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.enterprise.entity.EnterpriseUser;
/**
 * 企业用户
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface EnterpriseUserMasterMapper {
    int delete(Long id);

    int save(EnterpriseUser record);

    int update(EnterpriseUser record);
}