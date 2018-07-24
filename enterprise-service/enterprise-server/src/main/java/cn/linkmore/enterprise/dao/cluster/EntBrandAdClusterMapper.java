package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.enterprise.response.ResBrandAd;

/**
 * 品牌广告
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface EntBrandAdClusterMapper {
	
	// APP 使用接口
	/**
	 * 开屏广告
	 * @return
	 */
	List<ResBrandAd> findScreenList();
	/**
	 * 品牌车区广告
	 * @param map
	 * @return
	 */
	List<ResBrandAd> findBrandPreAdList(Map<String, Object> map);
	
	// OPS 使用接口
    ResBrandAd findById(Long id);

	Integer check(Map<String, Object> param);
	
	Integer count(Map<String, Object> param);
	
	List<ResBrandAd> findPage(Map<String, Object> param);
	
    
}