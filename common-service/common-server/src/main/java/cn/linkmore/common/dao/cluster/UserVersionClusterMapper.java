package cn.linkmore.common.dao.cluster;

import java.util.List;
import java.util.Map;

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

	/**
	 * @Description  查询总数
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<UserVersion> findPage(Map<String, Object> param);

}