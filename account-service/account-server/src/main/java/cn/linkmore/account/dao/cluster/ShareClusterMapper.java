package cn.linkmore.account.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.Share;

/**
 * 分享记录mapper(读)
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@Mapper
public interface ShareClusterMapper {
    /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    Share findById(Long id);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<Share> findPage(Map<String, Object> param);

	/**
	 * @Description  查询总数
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);
}