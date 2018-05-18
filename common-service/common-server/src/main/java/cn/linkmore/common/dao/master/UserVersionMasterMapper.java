package cn.linkmore.common.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.UserVersion;
/**
 * 用户版本mapper
 * @author   GFF
 * @Date     2018年5月17日
 * @Version  v2.0
 */
@Mapper
public interface UserVersionMasterMapper {
    /**
     * @Description	根据id删除  
     * @Author   GFF 
     * @Version  v2.0
     */
    int deleteById(Long userId);

    /**
     * @Description  新增
     * @Author   GFF 
     * @Version  v2.0
     */
    int insert(UserVersion record);

    /**
     * @Description  新增(null处理)
     * @Author   GFF 
     * @Version  v2.0
     */
    int insertSelective(UserVersion record);

    /**
     * @Description  更新(null处理)
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(UserVersion record);

    /**
     * @Description  更新
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(UserVersion record);
}