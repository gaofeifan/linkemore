package cn.linkmore.common.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.common.entity.BaseDict;
import cn.linkmore.common.response.ResBaseDict;
/**
 * 数据字词mapper(读)
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
    BaseDict findById(Long id);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResBaseDict> findListByCode(@Param("code")String code);

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	ResBaseDict find(Long id);

}