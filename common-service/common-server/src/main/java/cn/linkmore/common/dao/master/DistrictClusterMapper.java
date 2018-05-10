package cn.linkmore.common.dao.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.response.ResDistrict;
/**
 * Mapper - 区域信息操作[只读操作]
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface DistrictClusterMapper {
	
	/**
	 * 查询主键对应的区域信息
	 * @param id 主健
	 * @return ResDistrict区域信息
	 */
    ResDistrict findById(Long id); 
    
    /**
     * 根据条件查询区域列表
     * @param param 查询条件
     * @return 返回结果集
     */
    List<ResDistrict> findList(Map<String,Object> param); 
    
    /**
     * 分页查询
     * @param param 查询条件
     * @return  返回结果集
     */
    List<ResDistrict> findPage(Map<String,Object> param); 
    
    /**
     * 查询数量
     * @param param 条件
     * @return
     */
    Integer count(Map<String,Object> param);
    /**
     * 存在的记录数
     * @param param
     * @return
     */
    Integer exists(Map<String,Object> param);
}