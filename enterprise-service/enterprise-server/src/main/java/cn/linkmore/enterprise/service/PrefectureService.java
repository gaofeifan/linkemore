package cn.linkmore.enterprise.service;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.enterprise.controller.ent.response.ResCharge;
import cn.linkmore.enterprise.controller.ent.response.ResDayIncome;
import cn.linkmore.enterprise.controller.ent.response.ResDayTrafficFlow;
import cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount;
import cn.linkmore.order.response.ResChargeDetail;
import cn.linkmore.order.response.ResChargeList;
import cn.linkmore.order.response.ResIncome;
import cn.linkmore.order.response.ResIncomeList;
import cn.linkmore.order.response.ResTrafficFlow;
import cn.linkmore.order.response.ResTrafficFlowList;
import cn.linkmore.prefecture.response.ResStallBatteryLog;

/**
 * 车场运营实现
 * @author   GFF
 * @Date     2018年7月21日
 * @Version  v2.0
 */
public interface PrefectureService {

	/**
	 * @return 
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */

	List<ResPreOrderCount> findPreList(HttpServletRequest request);

	/**
	 * @param type 
	 * @param request 
	 * @return 
	 * @Description  查询今日收入
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	BigDecimal findPreDayIncome( Long preId, HttpServletRequest request);

	/**
	 * @param pageNo 
	 * @Description  根据条件查询实收入
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	cn.linkmore.enterprise.controller.ent.response.ResIncomeList findProceeds( Short type,Long preId, HttpServletRequest request);

	/**
	 * @param pageNo 
	 * @Description  查询车流量
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	cn.linkmore.enterprise.controller.ent.response.ResTrafficFlow findTrafficFlow( Short type,Long preId, HttpServletRequest request);

	/**
	 * @param pageNo 
	 * @Description  查询费明细
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<cn.linkmore.enterprise.controller.ent.response.ResChargeDetail> findChargeDetail( Integer pageNo, Long preId, HttpServletRequest request);

	/**
	 * @param pageNo 
	 * @param date 
	 * @Description  查询车流量列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResDayTrafficFlow> findTrafficFlowList(Integer pageNo, Short type, Long preId, String date, HttpServletRequest request);

	/**
	 * @param pageNo 
	 * @param date 
	 * @Description  查询收入列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResDayIncome> findIncomeList(Integer pageNo, Short type, Long preId, String date, HttpServletRequest request);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	/*List<ResCharge> findChargeDetailNew(Short type, Long preId,
			HttpServletRequest request);*/

	/**
	 * @Description  根据类型查询总金额
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	BigDecimal findProceedsAmount(Short type, Long preId, HttpServletRequest request);

	/**
	 * @Description  根据条件查询总流量
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer findTrafficFlowCount(Short type, Long preId, HttpServletRequest request);

	/**
	 * @Description  查询电池记录
	 * @Author   cl 
	 * @Version  v2.0
	 */
	List<ResStallBatteryLog>  StallBatteryLog(Long stallId);
	
}
