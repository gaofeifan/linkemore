package cn.linkmore.security.dao.cluster;

import java.util.List;
import java.util.Map;
import cn.linkmore.security.response.ResDictGroup;
/**
 * 字典分组
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface DictGroupClusterMapper {
	/**
	 * 查询实体
	 * @param id
	 * @return
	 */
    ResDictGroup findById(Long id);
    /**
     * 查询分组列表
     * @param param
     * @return
     */
	List<ResDictGroup> findList(Map<String, Object> param);
	/**
	 * 分页查询
	 * @param param
	 * @return
	 */
	List<ResDictGroup> findPage(Map<String, Object> param);
	/**
	 * 总数
	 * @param param
	 * @return
	 */
	Integer count(Map<String, Object> param);
	/**
	 * 校验字段
	 * @param param
	 * @return
	 */
	Integer check(Map<String, Object> param);

}