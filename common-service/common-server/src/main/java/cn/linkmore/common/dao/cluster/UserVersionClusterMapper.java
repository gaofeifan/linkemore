package cn.linkmore.common.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.UserVersion;
/**
 * 用户版本mapper
 * @author   GFF
 * @Date     2018年5月17日
 * @Version  v2.0
 */
@Mapper
public interface UserVersionClusterMapper {

    /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    UserVersion findById(Long userId);

}