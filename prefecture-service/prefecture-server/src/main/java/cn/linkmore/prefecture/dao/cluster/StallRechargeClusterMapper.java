package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.request.ReqStallRechargeExcel;
import cn.linkmore.prefecture.response.ResStallRecharge;
/**
 * dao 车位指定
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface StallRechargeClusterMapper {

	Integer count(Map<String, Object> param);

	List<ResStallRecharge> findPage(Map<String, Object> param);

	List<ResStallRecharge> findExcelList(ReqStallRechargeExcel bean);
}