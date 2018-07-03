package cn.linkmore.account.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.account.client.NoticeClient;
import cn.linkmore.account.request.ReqCreateNotice;
import cn.linkmore.account.request.ReqNotice;
import cn.linkmore.account.request.ReqPageNotice;
import cn.linkmore.account.response.ResNotice;
import cn.linkmore.account.response.ResNoticeBean;
import cn.linkmore.account.response.ResPage;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
/**
 * 消息管理--熔断
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Component
public class NoticeClientHystrix implements NoticeClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	

	@Override
	public ResPage listNotice(ReqPageNotice bean) {
		log.info("account service listNotice(ReqPageNotice bean) hystrix");
		return null;
	}


	@Override
	public ResNotice read(ReqNotice notice) {
		log.info("account service ResNotice read(ReqNotice notice) hystrix");
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void delete(ReqNotice notice) {
		log.info("account service void delete(ReqNotice notice) hystrix");
	}


	@Override
	public ViewPage selectList(ViewPageable pageable) {
		log.info("account service ViewPage selectList(ViewPageable pageable) hystrix");
		return null;
	}


	@Override
	public void save(ReqCreateNotice noticeBean) {
		log.info("account service void save(ReqCreateNotice noticeBean) hystrix");
		
	}


	@Override
	public ResNoticeBean detail(Long id) {
		log.info("account service ResNoticeBean detail(Long id)  hystrix");
		return null;
	}


	@Override
	public void update(ReqCreateNotice noticeBean) {
		log.info("account service void update(ReqCreateNotice noticeBean) hystrix");
		
	}


	@Override
	public void push(List<Long> ids) {
		log.info("account service void push(List<Long> ids) hystrix");
	}
	
	

	
	
}
