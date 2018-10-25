package cn.linkmore.enterprise.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.enterprise.controller.staff.response.MessageSearchResponseBean;
import cn.linkmore.enterprise.dao.cluster.StaffOperateClusterMapper;
import cn.linkmore.enterprise.entity.MobileMessage;
import cn.linkmore.enterprise.service.StaffOperateService;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.JsonUtils;




/**
 * 管理版实现
 * @author CHANLEI
 * @Date 2018年9月18日
 * @Version v1.0
 */
@Service
public class StaffOperateServiceImpl implements StaffOperateService{
	
	@Autowired 
	StaffOperateClusterMapper staffOperateClusterMapper;

	@Override
	public MessageSearchResponseBean getMessage(Long mobile) {
		MobileMessage message = staffOperateClusterMapper.findLatest(mobile);
		MessageSearchResponseBean  mes =new  MessageSearchResponseBean();
		Map<String,Object> param = new HashMap<String, Object>();
		if(message!=null) {
			param = JsonUtils.toObject(message.getParameter(), Map.class);
			mes.setContent( String.valueOf(param.get("code")));
			mes.setCreateTime(message.getCreateTime());
		}
		return mes;
	}

}
