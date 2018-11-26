package cn.linkmore.ops.biz.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStall;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.prefecture.response.ResStallOps;

/**
 * 
 * Service - 车位
 * @author jiaohanbin
 * @version 2.0
 */
public interface StallService {
	/**
	 * 查询树
	 * 
	 * @return
	 */
	Tree findTree(Map<String,Object> param);

	/**
	 * 查询分页
	 * 
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @return
	 */
	ResStallEntity findById(Long id);

	/**
	 * 保存
	 * 
	 * @param stall
	 */
	int save(ReqStall stall);

	/**
	 * 修改
	 * 
	 * @param stall
	 */
	int update(ReqStall stall);

	/**
	 * 校验名称
	 * 
	 * @param name
	 * @return
	 */
	int check(ReqCheck reqCheck);

	/**
	 * 绑定车位锁
	 * 
	 * @param stall
	 */
	int bind(Long id, Long sid);
	
	/**
	 * 取消绑定车位锁
	 * @param stallId
	 * @return
	 */
	int unBind(List<Long> ids);
	
	/**
	 * 保存并绑定
	 */
	void saveAndBind(Long preId, String stallName, String sn);
	/**
	 * 上线
	 * @param ids
	 */
	void changedUp(List<Long> ids);
	/**
	 * 下线
	 * @param id
	 */
	void changedDown(Long id);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResStallOps> findList(Map<String, Object> param);

	

}
