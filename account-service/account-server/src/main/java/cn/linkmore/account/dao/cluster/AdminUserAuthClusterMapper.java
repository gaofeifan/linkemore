package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.AdminUserAuth;
/**
 * 管理员用户权限
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface AdminUserAuthClusterMapper {

    /**
     * 根据id查询
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    AdminUserAuth selectById(Long id);

}