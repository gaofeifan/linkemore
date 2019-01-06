package cn.linkmore.ops.ent.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntUserPlate;

/**
 * 公司免费车牌用户
 * @author   GFF
 * @Date     2018年8月1日
 * @Version  v2.0
 */
public interface EntUserPlateService {

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findList(HttpServletRequest request, ViewPageable pageable);

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void save(ReqEntUserPlate plate);

	/**
	 * @Description  更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void update(ReqEntUserPlate plate);

	/**
	 * @Description  删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void delete(List<Long> ids);

	boolean exists(Map<String, Object> checkParam);

	int saveBatch(List<ReqEntUserPlate> plateList);

}
