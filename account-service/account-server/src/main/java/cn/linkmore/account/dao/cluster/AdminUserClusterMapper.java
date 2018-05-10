package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.AdminUser;



/**
 * 管理员--只读
 * @Version 2.0
 * @author  GFF
 * @Date     2018年5月10日
 */
@Mapper
public interface AdminUserClusterMapper { 
	
    AdminUser find(Long id);
    
    AdminUser findByMobile(String mobile);
}