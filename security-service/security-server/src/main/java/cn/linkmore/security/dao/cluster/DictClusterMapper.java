package cn.linkmore.security.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.security.response.ResDict;
/**
 * 字典详情
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface DictClusterMapper {
	/**
	 * 查询实体
	 * @param id
	 * @return
	 */
    ResDict findById(Long id);
    /**
     * 根据分类编码查询字典列表
     * @param code
     * @return
     */
    List<ResDict> findByGroupCode(String code);
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
	/**
	 * 分页
	 * @param param
	 * @return
	 */
	List<ResDict> findPage(Map<String, Object> param);
}