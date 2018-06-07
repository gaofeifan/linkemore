package cn.linkmore.order.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.entity.RechargeAmount;
/**
 * @author   GFF
 * @Date     2018年6月6日
 * @Version  v2.0
 */
@Mapper
public interface RechargeAmountClusterMapper {

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    RechargeAmount findById(Long id);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<RechargeAmount> findPage(Map<String, Object> param);

	/**
	 * @Description  查询总数
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);
}