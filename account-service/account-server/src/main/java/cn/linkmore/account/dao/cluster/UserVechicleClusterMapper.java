package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.UserVechicle;
/**
 * 用户车辆品牌
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface UserVechicleClusterMapper {

    /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    UserVechicle findById(Long id);

    /**
     * @Description  根据用户id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    UserVechicle findByUserId(Long userId);

}