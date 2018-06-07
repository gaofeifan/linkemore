package cn.linkmore.ops.biz.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStallLock;
import cn.linkmore.prefecture.response.ResStallLock;

public interface StallLockService {

	/**
	 * 分页查询
	 * 
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * 根据条件查询
	 * 
	 * @param param
	 * @return
	 */
	List<ResStallLock> find(Map<String, Object> param);

	/**
	 * 保存
	 * 
	 * @param record
	 */
	int save(ReqStallLock record);

	/**
	 * 更新
	 * 
	 * @param record
	 * @return
	 */
	int update(ReqStallLock record);

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	int delete(Long id);

	/**
	 * 检验属性存在
	 * 
	 * @return
	 */
	Boolean check(ReqCheck reqCheck);

	/**
	 * 批量插入数据
	 * 
	 * @param sns
	 */
	int batchSave(List<String> sns);

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	List<ResStallLock> findAll(Long lockId);

	/**
	 * 查看序列号
	 * 
	 * @param sn
	 * @return
	 */
	int checkSn(String sn);

	/**
	 * 验证是否是旧序列号
	 * 
	 * @param sn
	 * @return
	 */
	boolean checkFormerLock(String sn);

}
