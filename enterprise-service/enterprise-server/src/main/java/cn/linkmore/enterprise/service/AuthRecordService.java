package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.controller.app.request.ReqAuthRecord;
import cn.linkmore.enterprise.controller.app.request.ReqAuthRecordUpdate;
import cn.linkmore.enterprise.controller.app.response.AuthRecordPre;
import cn.linkmore.enterprise.entity.AuthRecord;
import cn.linkmore.enterprise.entity.EntRentedRecord;

public interface AuthRecordService {
	/**
	 * 查询列表
	 * @param pageable
	 * @return
	 */
	public ViewPage findPage(ViewPageable pageable);
	
	Boolean save(ReqAuthRecord record, HttpServletRequest request);
	
	Boolean update(ReqAuthRecordUpdate record, HttpServletRequest request);
	/**
	 * 取消授权
	 * @param id
	 * @param request 
	 * @return
	 */
	Boolean cancalAuth(Long id, HttpServletRequest request);
	/**
	 * 查看详情
	 * @param id
	 * @return
	 */
	AuthRecord findById(Long id);
	/**
	 * 根据车区id或者授权人id查询列表
	 * @param param
	 * @return
	 */
	List<AuthRecord> findRecordList(Map<String,Object> param);
	/**
	 * 查询授权人授权记录
	 * @param request
	 * @return
	 */
	public List<AuthRecordPre> findRecordList(HttpServletRequest request);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	AuthRecord findByUserId(Long userId, Long stallId);
	/**
	 * 启用禁用
	 * @param param
	 */
	public int operateSwitch(Map<String, Object> param);

	public List<AuthRecord> findAuthUserIdAndStallId(Long userId, Long stallId);

	Boolean shareStall(String stallIds, String mobile, HttpServletRequest request);
	/**
	 * 更新过期时间
	 */
	public int updateOverdueStatus();

	public List<EntRentedRecord> findAuthRecordByAuthUserId(Long id);

}
