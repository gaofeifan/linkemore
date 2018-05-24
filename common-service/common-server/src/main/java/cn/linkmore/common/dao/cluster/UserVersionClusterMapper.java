package cn.linkmore.common.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.common.entity.UserVersion;
import cn.linkmore.common.response.ResVersionBean;
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