package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.AdminAuth;
/**
 * 管理员权限
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface AdminAuthClusterMapper {
    /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    AdminAuth selectById(Long id);
}