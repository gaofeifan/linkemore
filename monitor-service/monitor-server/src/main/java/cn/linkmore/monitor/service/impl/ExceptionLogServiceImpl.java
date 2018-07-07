package cn.linkmore.monitor.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.monitor.dao.master.ExceptionLogMapper;
import cn.linkmore.monitor.entity.ExceptionLog;
import cn.linkmore.monitor.service.ExceptionLogService;
import cn.linkmore.monitor.service.WechatService;
import cn.linkmore.util.DateUtils;

/**
 * 异常日志记录
 * @author   GFF
 * @Date     2018年7月4日
 * @Version  v2.0
 */
@Service
public class ExceptionLogServiceImpl implements ExceptionLogService {

	@Resource
	private ExceptionLogMapper exceptionLogMapper;
	@Resource
	private WechatService wechatService;
	@Override
	public void save(ExceptionLog log) {
		this.exceptionLogMapper.saveSelective(log);
		Map<String,Object> mapData = new HashMap<>();
		Map<String,Object> map = new HashMap<>();
		map.put("value", "异常日志推送");
		map.put("color", "#173177");
		mapData.put("first", map);
		map = new HashMap<>();
		map.put("value", log.getLevel());
		map.put("color", "#173177");
		mapData.put("type", map);
		map = new HashMap<>();
		map.put("value", log.getContent());
		map.put("color", "#173177");
		mapData.put("content", map);
		map = new HashMap<>();
		map.put("value", DateUtils.converter(log.getCreateTime(),"yyyy-MM-dd"));
		map.put("color", "#173177");
		mapData.put("date3", map);
		map = new HashMap<>();
		map.put("value", log.getUrl());
		map.put("color", "#173177");
		mapData.put("requestUrl", map);
		map = new HashMap<>();
		map.put("value", log.getParameter());
		map.put("color", "#173177");
		mapData.put("methodUrl", map);
		/*map = new HashMap<>();
		map.put("value", log.getLevel());
		map.put("color", "#173177");
		mapData.put("remark", map);*/
		this.wechatService.pushTemplateMsg(mapData);
	}

	@Override
	public List<ExceptionLog> list() {
		return this.exceptionLogMapper.findAll();
	}

	
}
