package cn.linkmore.order.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.RechargeRecord;
import cn.linkmore.order.request.ReqRechargeRecordExcel;
import cn.linkmore.order.response.ResRechargeRecordExcel;
/**
 * @author   GFF
 * @Date     2018年6月6日
 * @Version  v2.0
 */
@Mapper
public interface RechargeRecordClusterMapper {

    RechargeRecord findById(Integer id);

	RechargeRecord findByNumber(String number);

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
	List<ResRechargeRecordExcel> findPage(Map<String, Object> param);

	/**
	 * @Description  查询导出数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResRechargeRecordExcel> findExportList(ReqRechargeRecordExcel bean);

}