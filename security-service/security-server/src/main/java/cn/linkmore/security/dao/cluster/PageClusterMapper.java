package cn.linkmore.security.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.Page;
/**
 * 页面
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface PageClusterMapper {

    Page findById(Long id);
    /**
     * 总数
     * @param param
     * @return
     */
	Integer count(Map<String, Object> param);
	/**
	 * 分页
	 * @param param
	 * @return
	 */
	List<Page> findPage(Map<String, Object> param);
	/**
	 * 校验字段
	 * @param param
	 * @return
	 */
	Integer check(Map<String, Object> param);
	/**
	 * 查询所有页面
	 * @return
	 */
	List<Page> findAll();

}