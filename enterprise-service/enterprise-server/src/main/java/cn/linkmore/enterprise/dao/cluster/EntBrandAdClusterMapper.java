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
	
    ResBrandAd findById(Long id);

	Integer check(Map<String, Object> param);
	
    
}