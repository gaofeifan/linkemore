package cn.linkmore.security.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.security.entity.Clazz;
/**
 * 类
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface ClazzClusterMapper {
	/**
	 * 根据id查找实体类
	 * @param id
	 * @return
	 */
    Clazz findById(Long id);
    /**
     * 查询总数
     * @param param
     * @return
     */
	Integer count(Map<String, Object> param);
	/**
	 * 分页查询
	 * @param param
	 * @return
	 */
	List<Clazz> findPage(Map<String, Object> param);
	/**
	 * 检查是否存在
	 * @param param
	 * @return
	 */
	Integer check(Map<String, Object> param);
	/**
	 * 返回所有的类
	 * @return
	 */
	List<Clazz> findAll();

}