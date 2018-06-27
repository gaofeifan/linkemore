package cn.linkmore.account.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.NoticeClusterMapper;
import cn.linkmore.account.dao.cluster.NoticeContentClusterMapper;
import cn.linkmore.account.dao.cluster.NoticeReadClusterMapper;
import cn.linkmore.account.dao.master.NoticeContentMasterMapper;
import cn.linkmore.account.dao.master.NoticeMasterMapper;
import cn.linkmore.account.dao.master.NoticeReadMasterMapper;
import cn.linkmore.account.entity.Notice;
import cn.linkmore.account.entity.NoticeContent;
import cn.linkmore.account.entity.NoticeRead;
import cn.linkmore.account.request.ReqCreateNotice;
import cn.linkmore.account.request.ReqNotice;
import cn.linkmore.account.response.ResNotice;
import cn.linkmore.account.response.ResNoticeBean;
import cn.linkmore.account.response.ResPage;
import cn.linkmore.account.response.ResPageNotice;
import cn.linkmore.account.service.NoticeService;
import cn.linkmore.account.service.UserService;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.TokenUtil;

/**
 * @author GFF
 * @Date 2018年6月9日
 * @Version v2.0
 */
@Service
public class NoticeServiceImpl implements NoticeService {

	private static final Long NOTICE_OPER_READ =1l;
	@Resource
	private NoticeClusterMapper noticeClusterMapper;
	@Resource
	private NoticeMasterMapper noticeMasterMapper;
	@Resource
	private NoticeContentClusterMapper contentClusterMapper;
	@Resource
	private NoticeContentMasterMapper contentMasterMapper;
	@Resource
	private NoticeReadClusterMapper clusterMapper;
	@Resource
	private NoticeReadMasterMapper readMasterMapper;
	@Resource
	private RedisService redisService;
	@Resource
	private UserService userService;
	@Override
	public ResPage page(Long start, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		Map<String, Object> map = new HashMap<>();
		map.put("start", start);
		map.put("uid", ru.getId());
		List<ResPageNotice> resPageNotic = noticeClusterMapper.findPageNotice(map);
		List<Long> longs = noticeClusterMapper.findNoticeReadDel( ru.getId());
		Date now = new Date();
		for (int i = 0; i < resPageNotic.size(); i++) {
			Date time = resPageNotic.get(i).getPushTime();
			String duration = DateUtils.getDuration(now, time);
			resPageNotic.get(i).setPushedTime(duration);
			for (Long del : longs) {
				if (resPageNotic.get(i).getId() == del) {
					resPageNotic.get(i).setRead_status(NOTICE_OPER_READ);
				}
			}
		}
		ResPage<ResPageNotice> page = new ResPage<ResPageNotice>();
		int i = noticeClusterMapper.findNotReadCount( ru.getId());
		Integer flag = this.clusterMapper.findNotReadNotice();
		page.setRows(resPageNotic);
		page.setRecords((long) i);
		if(flag > 0) {
			page.setStatus(true);
		}else {
			page.setStatus(false);
		}
		return page;
	}

	@Override
	public ResNotice read(ReqNotice reqNotice) {
		String content = "";
        Map<String,Object> map = new HashMap<>();
        map.put("nid",reqNotice.getNid());
        map.put("uid",reqNotice.getUserId());
        NoticeRead notReadByNid = clusterMapper.findNotReadByNid(map);
        if(null != notReadByNid ){
            Notice notice = noticeClusterMapper.findById(reqNotice.getNid());
            if(notice.getType() ==1){
            	content = notice.getUrl();
            }else if(notice.getType() ==0){
            	content = contentClusterMapper.findById(reqNotice.getNid()).getContent();

            }else {
            	content = "open couponList";
            }


        }else {

        NoticeRead noticeRead = new NoticeRead();
        noticeRead.setDeleteStatus(0l);
        noticeRead.setCreateTime(new Date());
        noticeRead.setUserId(reqNotice.getUserId());
        noticeRead.setNoticeId(reqNotice.getNid());
        noticeRead.setUpdateTime(new Date());
        noticeRead.setReadStatus(NOTICE_OPER_READ);
        readMasterMapper.save(noticeRead);
        Notice notice = noticeClusterMapper.findById(reqNotice.getNid());

        if(notice.getType() ==1){
        	content = notice.getUrl();
        }else if(notice.getType() ==0){
        	content = contentClusterMapper.findById(reqNotice.getNid()).getContent();

        }else {
        	content = "open couponList";
        }
        }
        
        int i = this.noticeClusterMapper.findNotReadCount(reqNotice.getUserId());
        ResNotice noticeResponseBean = new ResNotice();
        noticeResponseBean.setContent(content);
        noticeResponseBean.setCount((long)i);

		return noticeResponseBean;
	}

	@Override
	public void delete(ReqNotice notice) {
	  NoticeRead noticeRead = this.noticeClusterMapper.findNoticeReadByNid(notice);
		if (null != noticeRead) {
			noticeRead.setUpdateTime(new Date());
			noticeRead.setDeleteStatus	(1l);// 删除
			readMasterMapper.updateByIdSelective(noticeRead);
		} else {
			  NoticeRead noticeread = new NoticeRead();
			  noticeread.setDeleteStatus(NOTICE_OPER_READ);
			  noticeread.setCreateTime(new Date());
			  noticeread.setUserId(notice.getUserId());
			  noticeread.setNoticeId(notice.getNid());
			  noticeread.setUpdateTime(new Date());
			  noticeread.setReadStatus(0l);
	          readMasterMapper.save(noticeRead);
		}
	}

	@Override
	public ResNotice read(Long id, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key);
		ReqNotice no = new ReqNotice();
		no.setNid(id);
		no.setUserId(ru.getId());
		return this.read( no);
	}

	@Override
	public void delete(Long nid, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key);
		ReqNotice notice = new ReqNotice();
		notice.setNid(nid);
		notice.setUserId(ru.getId());
		this.delete(notice );
	}

	@Override
	public ViewPage selectList(ViewPageable pageable) {
		Integer count;
		List<Notice> list;
		try {
			Map<String,Object> param = new HashMap<String,Object>(); 
			List<ViewFilter> filters = pageable.getFilters();
			if(StringUtils.isNotBlank(pageable.getSearchProperty())) {
				param.put(pageable.getSearchProperty(), pageable.getSearchValue());
			}
			if(filters!=null&&filters.size()>0) {
				for(ViewFilter filter:filters) {
					param.put(filter.getProperty(), filter.getValue());
				}
			}
			if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
				param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty().replaceAll(" ", "")));
				param.put("direction", pageable.getOrderDirection());
			}
			count = this.noticeClusterMapper.count(param);
			list = new ArrayList<Notice>();
			if(count>0){
				param.put("start", pageable.getStart());
				param.put("pageSize", pageable.getPageSize());
				list = this.noticeClusterMapper.findPage(param);
			}
			return new ViewPage(count,pageable.getPageSize(),list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void saveNotice(ReqCreateNotice noticeBean) {
		Notice notice = new Notice();
		notice.setType(noticeBean.getType());
		notice.setTitle(noticeBean.getTitle());
		notice.setDescription(noticeBean.getDescription());
		notice.setStatus(1l);
		notice.setCreateTime(new Date());
		if(noticeBean.getType()==1){
			notice.setUrl(noticeBean.getUrl());
		}else{
			//推送时URL不能为空
			notice.setUrl("ignore");
		}
		noticeMasterMapper.saveSelective(notice);
		NoticeContent noticeContent = new NoticeContent();
		noticeContent.setId(notice.getId());
		noticeContent.setContent(noticeBean.getContent());
		contentMasterMapper.saveSelective(noticeContent);
	}

	@Override
	public ResNoticeBean selectById(Long id) {
		Notice notice = this.noticeClusterMapper.findById(id);
		return ObjectUtils.copyObject(notice, new ResNoticeBean());
				
	}

	@Override
	public void updateNotice(ReqCreateNotice noticeBean) {
		Notice notice = noticeClusterMapper.findById(noticeBean.getId());
		notice.setType(noticeBean.getType());
		notice.setTitle(noticeBean.getTitle());
		notice.setDescription(noticeBean.getDescription());
		if(noticeBean.getType()==1){
			notice.setUrl(noticeBean.getUrl());
		}else{
			//推送时URL不能为空
			notice.setUrl("ignore");
		}
		notice.setCreateTime(new Date());
		noticeMasterMapper.updateByIdSelective(notice);
		NoticeContent noticeContent = new NoticeContent();
		noticeContent.setId(notice.getId());
		noticeContent.setContent(noticeBean.getContent());
		contentMasterMapper.updateByIdSelective(noticeContent);
	}

	@Override
	public void pushNotice(Long id) {
		Notice notice = noticeClusterMapper.findById(id);
		NoticeContent noticeContent = contentClusterMapper.findById(notice.getId());
		
		//获取 所有APP用户的userId,
		/*List<BigInteger> userIds = userRepository.findAllAppUserId(1);
		for (BigInteger userId : userIds) {
			//推送通知
			pushNotice(notice,noticeContent.getContent(),userId.toString());
		}*/
//		pushNotice(notice,noticeContent.getContent());
		notice.setPushTime(new Date());
		notice.setStatus(0l);
		noticeMasterMapper.updateByIdSelective(notice);		
	}
	
	@Override
	public void pushNotice(List<Long> ids) {
		for (Long id : ids) {
			this.pushNotice(id);
		}
	}

	@Override
	public void updateRead() {
		this.readMasterMapper.updateReadStatus();
	}
	
	
	
	
}
