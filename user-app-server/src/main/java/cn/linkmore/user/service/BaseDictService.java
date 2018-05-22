package cn.linkmore.user.service;

import java.util.List;

import cn.linkmore.common.response.ResBaseDict;
import cn.linkmore.user.response.ResDonwLockError;

/**
 * 数据字典操作
 * @author   GFF
 * @Date     2018年5月21日
 * @Version  v2.0
 */
public interface BaseDictService {

	/**
	 * @Description  查询降锁异常原因
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResDonwLockError> selectLockDownErrorCause();

}
