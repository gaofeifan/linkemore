package cn.linkmore.common.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.common.entity.BaseDict;
/**
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface BaseDictClusterMapper {

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    BaseDict selectById(Long id);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<BaseDict> selectListByCode(@Param("code")String code);

}