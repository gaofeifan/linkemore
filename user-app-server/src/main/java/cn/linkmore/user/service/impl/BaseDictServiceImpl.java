package cn.linkmore.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.common.client.BaseDictClient;
import cn.linkmore.common.response.ResBaseDict;
import cn.linkmore.user.service.BaseDictService;
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
	public List<ResBaseDict> selectLockDownErrorCause() {
		List<ResBaseDict> list = baseDictClient.selectList(DOWN_LOCK_ERROR_CAUSE);
		return list;
	}

	
}
