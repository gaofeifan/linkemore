package cn.linkmore.ops.ent.service.impl;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.ent.service.AuthRecordService;
import cn.linkmore.prefecture.client.AuthRecordClient;

/**
 * 授权记录--接口实现
 * @author   GFF
 * @Date     2018年7月30日
 * @Version  v2.0
 */
@Service
public class AuthRecordServiceImpl implements AuthRecordService {

	@Resource
	private AuthRecordClient authRecordClient;

	@Override
	public ViewPage findList(HttpServletRequest request, ViewPageable pageable) {
		return this.authRecordClient.findList(pageable);
	}

	@Override
	public int operateSwitch(Map<String, Object> param) {
		return this.authRecordClient.operateSwitch(param);
	}
}
