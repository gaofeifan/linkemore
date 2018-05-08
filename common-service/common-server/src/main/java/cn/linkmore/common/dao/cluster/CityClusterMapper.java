package cn.linkmore.common.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.response.ResCity;
/**
 * Mapper - 城市信息操作[只读操作]
 * @author liwenlong
 * @version 2.0
 *
 */
@Mapper
public interface CityClusterMapper {
	
	/**
	 * 查询主键对应的城市信息
	 * @param id 主健
	 * @return ResCity城市信息
	 */
    ResCity findById(Long id); 
    
    /**
     * 根据行政编号查询城市信息
     * @param adcode  行政编号
     * @return ResCity 城市信息
     */
    ResCity findByCode(String adcode);
    
    /**
     * 根据条件查询城市列表
     * @param param 查询条件
     * @return 返回结果集
     */
    List<ResCity> findList(Map<String,Object> param); 
    
    /**
     * 分页查询
     * @param param 查询条件
     * @return  返回结果集
     */
    List<ResCity> findPage(Map<String,Object> param); 
    
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