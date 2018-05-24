package cn.linkmore.common.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.common.entity.BaseAppVersion;
import cn.linkmore.common.response.ResVersionBean;
/**
 * app版本管理(读)
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface BaseAppVersionClusterMapper {


    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    BaseAppVersion findById(Long id);

	/**
	 * @Description  根据状态类型查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResVersionBean> findByTypeAnStatus(@Param("appType")Integer appType,@Param("status") Integer status);
}