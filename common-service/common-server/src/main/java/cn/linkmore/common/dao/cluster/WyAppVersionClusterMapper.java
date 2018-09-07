package cn.linkmore.common.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.common.entity.BaseAppVersion;
import cn.linkmore.common.entity.WyAppVersion;
import cn.linkmore.common.response.ResVersionBean;
import cn.linkmore.common.response.ResWyAppVersion;
/**
 * 物业版本管理(读)
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface WyAppVersionClusterMapper {


    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    WyAppVersion findById(Long id);

	/**
	 * @Description  根据状态类型查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResWyAppVersion> findByTypeAnStatus(Map<String, Object> param);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<WyAppVersion> findPage(Map<String, Object> param);

	/**
	 * @Description  查询总数
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer check(Map<String, Object> param);

	/**
	 * @Description  查询最后的版本
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResWyAppVersion findLast(Integer appType);

}