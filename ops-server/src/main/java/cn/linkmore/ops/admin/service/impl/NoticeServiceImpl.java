package cn.linkmore.ops.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.account.client.NoticeClient;
import cn.linkmore.account.response.ResNoticeBean;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.request.ReqCreateNotice;
import cn.linkmore.ops.admin.response.ResNotice;
import cn.linkmore.ops.admin.service.NoticeService;
import cn.linkmore.util.ObjectUtils;

/**
 * 消息接口实现
 * @author   GFF
 * @Date     2018年6月22日
 * @Version  v2.0
 */
@Service
public class NoticeServiceImpl implements NoticeService {

	@Resource
	private NoticeClient noticeClient;

	@Override
	public ViewPage selectList(ViewPageable pageable) {
		return this.noticeClient.selectList(pageable);
	}

	@Override
	public ResNotice selectById(Long id) {
		ResNoticeBean detail = this.noticeClient.detail(id);
		ResNotice notice = ObjectUtils.copyObject(detail, new ResNotice());
		return notice;
	}

	@Override
	public void updateNotice(ReqCreateNotice noticeBean) {
		cn.linkmore.account.request.ReqCreateNotice object = ObjectUtils.copyObject(noticeBean, new cn.linkmore.account.request.ReqCreateNotice());
		this.noticeClient.update(object);
	}

	@Override
	public void pushNotice(List<Long> ids) {
		this.noticeClient.push(ids);
	}

	@Override
	public void saveNotice(ReqCreateNotice noticeBean) {
		cn.linkmore.account.request.ReqCreateNotice object = ObjectUtils.copyObject(noticeBean, new cn.linkmore.account.request.ReqCreateNotice());
		this.noticeClient.save(object);
	}
	
}
