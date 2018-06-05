package cn.linkmore.account.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.RechargeRecord;
import cn.linkmore.account.request.ReqRechargeRecordExcel;
import cn.linkmore.account.response.ResRechargeRecordExcel;
/**
 * 储值记录--mapper--读
 * @author   GFF
 * @Date     2018年5月31日
 * @Version  v2.0
 */
@Mapper
public interface RechargeRecordClusterMapper {

    /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    RechargeRecord findById(Integer id);

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
	List<ResRechargeRecordExcel> findPage(Map<String, Object> param);

	/**
	 * @Description  根据条件查询导出excel数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResRechargeRecordExcel> findExportList(ReqRechargeRecordExcel bean);

}