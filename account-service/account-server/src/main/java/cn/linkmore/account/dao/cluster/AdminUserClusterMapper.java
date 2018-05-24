package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.AdminUser;
/**
 * 管理员用户(读)
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface AdminUserClusterMapper {


    /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    AdminUser findById(Long id);

}