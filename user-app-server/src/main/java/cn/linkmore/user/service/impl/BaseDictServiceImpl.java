package cn.linkmore.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.common.client.BaseDictClient;
import cn.linkmore.common.response.ResBaseDict;
import cn.linkmore.user.response.ResDonwLockError;
import cn.linkmore.user.service.BaseDictService;
import cn.linkmore.util.ObjectUtils;
/**
 * @author   GFF
 * @Date     2018年5月21日
 * @Version  v2.0
 */
@Service
public class BaseDictServiceImpl implements BaseDictService {

	public static final String DOWN_LOCK_ERROR_CAUSE = "";

	@Resource
	private BaseDictClient baseDictClient;
	
	@Override
	public List<ResDonwLockError> selectLockDownErrorCause() {
		List<ResBaseDict> list = baseDictClient.findList(DOWN_LOCK_ERROR_CAUSE);
		List<ResDonwLockError> resultList = new ArrayList<>();
		for (ResBaseDict resBaseDict : list) {
			ResDonwLockError lockError = ObjectUtils.copyObject(resBaseDict,new ResDonwLockError());
			resultList.add(lockError);
		}
		return resultList;
	}

	
}
