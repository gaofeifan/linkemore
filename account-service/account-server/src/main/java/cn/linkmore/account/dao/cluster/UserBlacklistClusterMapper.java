package cn.linkmore.account.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.UserBlacklist;
import cn.linkmore.account.response.ResUserBlacklist;
/**
 * - 权限模块mapper -- 读
 * @author   GFF
 * @Date     2018年6月20日
 * @Version  v2.0
 */
@Mapper
public interface UserBlacklistClusterMapper {

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    UserBlacklist findById(Long id);

	/**
	 * @Description  查询总数
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResUserBlacklist> findPage(Map<String, Object> param);

	/**
	 * @Description  查询所有
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<UserBlacklist> findList();

}