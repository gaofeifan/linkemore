package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.UserStaff;
/**
 * 
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface UserStaffClusterMapper {
    /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    UserStaff findById(Long id);
}