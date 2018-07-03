package cn.linkmore.common.service;

import java.util.List;

import cn.linkmore.common.controller.app.response.ResDonwLockError;
import cn.linkmore.common.request.ReqBaseDict;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.response.ResBaseDict;

/**
 * Service接口 - 区域信息
 * @author liwenlong
 * @version 1.0
 *
 */
public interface BaseDictService {

	/**
	 * @Description  根据code查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResBaseDict> findList(String code);

	/**
	 * @Description		新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void save(ReqBaseDict baseDict);

	/**
	 * @Description 	通过id删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void deleteById(Long id);

	/**
	 * @Description  通过id更新(null处理)
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateByIdSelective(ReqBaseDict baseDict);

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	ResBaseDict find(Long id);

	/**
	 * @Description  查询降锁异常原因-app
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResDonwLockError> selectLockDownErrorCause();

}
