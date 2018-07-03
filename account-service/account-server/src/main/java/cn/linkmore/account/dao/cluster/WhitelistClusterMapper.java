package cn.linkmore.account.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.Whitelist;
/**权限模块 - 类记录-- 读mapper
 * @author   GFF
 * @Date     2018年6月22日
 * @Version  v2.0
 */
@Mapper
public interface WhitelistClusterMapper {

    /**
     * @Description  查询详情
     * @Author   GFF 
     * @Version  v2.0
     */
    Whitelist findById(Long id);

	/**
	 * @Description	查询总数  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<Whitelist> findPage(Map<String, Object> param);

	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer check(Map<String, Object> param);
}