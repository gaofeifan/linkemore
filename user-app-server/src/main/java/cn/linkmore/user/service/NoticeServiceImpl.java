package cn.linkmore.user.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.account.client.NoticeClient;
import cn.linkmore.account.request.ReqNotice;
import cn.linkmore.account.request.ReqPageNotice;
import cn.linkmore.account.response.ResNotice;
import cn.linkmore.account.response.ResPage;
import cn.linkmore.user.response.ResUser;

/**
 * @author   GFF
 * @Date     2018年6月11日
 * @Version  v2.0
 */
@Service
public class NoticeServiceImpl implements NoticeService {

	@Resource
	private NoticeClient noticeClient;
	@Resource
	private UserService userService;
	
	@SuppressWarnings("rawtypes")
	@Override
	public ResPage findPage(ReqPageNotice bean) {
		ResPage listNotice = noticeClient.listNotice(bean);
		return listNotice;
	}

	@Override
	public ResNotice read(Long id, HttpServletRequest request) {
		ResUser user = this.userService.getCache(request);
		ReqNotice notice = new ReqNotice();
		notice.setNid(id);
		notice.setUserId(user.getId());
		ResNotice read = this.noticeClient.read(notice );
		return read;
	}

	@Override
	public void delete(Long nid, HttpServletRequest request) {
		ResUser user = this.userService.getCache(request);
		ReqNotice notice = new ReqNotice();
		notice.setNid(nid);
		notice.setUserId(user.getId());
		this.noticeClient.delete(notice );
	}

	
	
}
