package cn.linkmore.order.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.order.entity.RechargeAmount;
import cn.linkmore.order.request.ReqRechargeAmount;
import cn.linkmore.order.request.ReqUpdateSql;
@Mapper
public interface RechargeAmountMasterMapper {
    int delete(Long id);

    int save(ReqRechargeAmount rechargeAmount);

    int update(RechargeAmount record);

	/**
	 * @Description  更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateColumnValue(ReqUpdateSql sql);

}