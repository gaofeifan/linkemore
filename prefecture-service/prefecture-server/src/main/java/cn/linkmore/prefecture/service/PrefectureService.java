package cn.linkmore.prefecture.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.controller.app.request.ReqBooking;
import cn.linkmore.prefecture.controller.app.request.ReqNearPrefecture;
import cn.linkmore.prefecture.controller.app.request.ReqPrefecture;
import cn.linkmore.prefecture.controller.app.response.ResAppointGroupDetail;
import cn.linkmore.prefecture.controller.app.response.ResGroupStrategy;
import cn.linkmore.prefecture.controller.app.response.ResPreCity;
import cn.linkmore.prefecture.controller.app.response.ResPrefectureList;
import cn.linkmore.prefecture.controller.app.response.ResPrefectureStrategy;
import cn.linkmore.prefecture.controller.app.response.ResStallInfo;
import cn.linkmore.prefecture.controller.staff.response.ResGateway;
import cn.linkmore.prefecture.controller.staff.response.ResLockGatewayList;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqPreExcel;
import cn.linkmore.prefecture.request.ReqPrefectureEntity;
import cn.linkmore.prefecture.response.ResGatewayDetails;
import cn.linkmore.prefecture.response.ResGatewayGroup;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPreExcel;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResPrefecture;
import cn.linkmore.prefecture.response.ResPrefectureDetail;

/**
 * Service接口 - 车区信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
/**
 * @author   GFF
 * @Date     2019年2月19日
 * @Version  v2.0
 */
public interface PrefectureService {

	/**
	 * 根据ID查询对应的车区信息
	 * @param id 主键
	 * @return
	 */
	ResPrefectureDetail findById(Long id);
	
	/**
	 * 根据车区id查询计费策略
	 * @param preId
	 * @return
	 */
	ResPrefectureStrategy findPreStrategy(Long preId);
	/**
	 * 根据车区id集合查询车名名称集合
	 * @param ids
	 * @return
	 */
	List<ResPre> findList(List<Long> ids);
	/**
	 * 查询所有车区空闲车位
	 * @return
	 */
	List<ResPrefectureList> getStallCount(HttpServletRequest request);
	
	/**
	 * 查询分页
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);
	/**
	 * 校验字段名称
	 * @param reqCheck
	 * @return
	 */
	Integer check(ReqCheck reqCheck);
	/**
	 * 获取车区列表
	 * @return
	 */
	List<ResPreList> findSelectList();
	/**
	 * 新增
	 * @param prefecture
	 * @return
	 */
	int save(ReqPrefectureEntity prefecture);
	
	/**
	 * 更新
	 * @param prefecture
	 * @return
	 */
	int update(ReqPrefectureEntity prefecture);
	/**
	 * 获取城市下车区列表
	 * @param cityId
	 * @return
	 */
	List<Map<String, Object>> findByCity(Long cityId);
	/**
	 * 导出车区列表
	 * @param reqPreExcel
	 * @return
	 */
	List<ResPreExcel> exportList(ReqPreExcel reqPreExcel);
	
	/**
	 * 车区树
	 * @return
	 */
	Tree findTree(Map<String, Object> param);
	
	ResPrefectureDetail checkName(Map<String, Object> param);
	/**
	 * 根据当前位置获取车区分组列表
	 * @param reqPrefecture
	 * @return
	 */
	List<ResPreCity> list(ReqPrefecture rp, HttpServletRequest request);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResPrefecture> findPreList();

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResPrefectureDetail> findList(Map<String, Object> param);
	/**
	 * 根据车区id查询空闲车位数
	 * @param reqBooking
	 * @return
	 */
	ResStallInfo findStallList(ReqBooking reqBooking);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResPre> findPreByIds(Map<String, Object> map);
	
	cn.linkmore.prefecture.controller.app.response.ResPrefectureDetail findPreDetailById(Long preId, HttpServletRequest request);

	List<ResPreList> findSelectListByUser(Map<String, Object> param);

	List<cn.linkmore.prefecture.controller.app.response.ResPrefecture> nearList(ReqNearPrefecture rp,
			HttpServletRequest request);

	ResGroupStrategy findGroupStrategy(Long groupId, HttpServletRequest request);

	Boolean checkPlate(Long plateId, HttpServletRequest request);
	/**
	 * 预约时查看车区内车位详情
	 * @param reqBooking
	 * @param request
	 * @return
	 */
	ResAppointGroupDetail findAppointGroupDetail(ReqBooking reqBooking, HttpServletRequest request);

	/**
	 * @Description  绑定网关
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean bindGroup(Long preId, String serialNumber, HttpServletRequest request);

	/**
	 * @Description  接触绑定
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean unBindGroup(String groupCode, String serialNumber, HttpServletRequest request);

	/**
	 * @Description  查询车区网关list
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResGateway> findGatewayGroup(Long preId, HttpServletRequest request);

	/**
	 * @Description  查询网关(扫一扫)
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	cn.linkmore.prefecture.controller.staff.response.ResGatewayDetails getGatewayDetails(String serialNumber, HttpServletRequest request);

	/**
	 * @param serialNumber 
	 * @Description  加载锁
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean loadLock(HttpServletRequest request, String serialNumber);

	/**
	 * @Description  重启网关
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean restartGateway(HttpServletRequest request, String serialNumber);

	/**
	 * @Description  解除绑定锁
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean unBindLock(String lockSn, String serialNumber, HttpServletRequest request);


}
