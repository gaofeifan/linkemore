package cn.linkmore.security.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.response.ResLog;
/**
 * 日志
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface LogClusterMapper {
	
    ResLog findById(Long id);
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
	List<ResLog> findPage(Map<String, Object> param);

}