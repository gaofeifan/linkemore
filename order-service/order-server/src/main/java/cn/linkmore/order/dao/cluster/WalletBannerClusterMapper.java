package cn.linkmore.order.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.WalletBanner;
import cn.linkmore.order.response.ResWalletBanner;
/**
 * banner管理--mapper-读
 * @author   GFF
 * @Date     2018年5月31日
 * @Version  v2.0
 */
@Mapper
public interface WalletBannerClusterMapper {

    /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    WalletBanner findById(Long id);

	/**
	 * @Description  自定义拼接查询条件查询数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResWalletBanner> findList(String sql);

	/**
	 * @Description  根据条件查询总数
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<WalletBanner> findPage(Map<String, Object> param);

}