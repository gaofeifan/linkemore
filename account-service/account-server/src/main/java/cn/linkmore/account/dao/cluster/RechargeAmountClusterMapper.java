package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.RechargeAmount;
/**
 * 充值面额--mapper（读）
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Mapper
public interface RechargeAmountClusterMapper {
	  /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    RechargeAmount findById(Long id);
}