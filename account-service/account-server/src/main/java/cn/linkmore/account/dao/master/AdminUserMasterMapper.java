package cn.linkmore.account.dao.master;
import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.AdminUser;

/**
 * 管理员--只写
 * @Version 2.0
 * @author  GFF
 * @Date     2018年5月10日
 */
@Mapper
public interface AdminUserMasterMapper { 
    int update(AdminUser record); 
}