package cn.linkmore.account.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.master.MessageMasterMapper;
import cn.linkmore.account.request.ReqMessage;
import cn.linkmore.account.service.MessageService;
/**
 * @author   GFF
 * @Date     2018年10月24日
 * @Version  v2.0
 */
@Service
public class MessageServiceImpl implements MessageService {

	@Resource
	private MessageMasterMapper messageMasterMapper;
	@Override
	public void save(ReqMessage message) {
		this.messageMasterMapper.save(message);
	}

}
