package cn.linkmore.security.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.Interface;
/**
 * 接口
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface InterfaceClusterMapper {
	/**
	 * 查询实体
	 * @param id
	 * @return
	 */
    Interface findById(Long id);
    /**
     * 检验字段
     * @param param
     * @return
     */
	Integer check(Map<String, Object> param);
	/**
	 * 分页查询
	 * @param param
	 * @return
	 */
	List<Interface> findPage(Map<String, Object> param);
	/**
	 * 查询总数
	 * @param param
	 * @return
	 */
	Integer count(Map<String, Object> param);
	/**
	 * 查询所有接口
	 * @return
	 */
	List<Interface> findAll();

}