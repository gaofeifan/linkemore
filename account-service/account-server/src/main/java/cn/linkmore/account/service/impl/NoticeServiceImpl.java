package cn.linkmore.account.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.NoticeClusterMapper;
import cn.linkmore.account.dao.cluster.NoticeContentClusterMapper;
import cn.linkmore.account.dao.cluster.NoticeReadClusterMapper;
import cn.linkmore.account.dao.master.NoticeContentMasterMapper;
import cn.linkmore.account.dao.master.NoticeMasterMapper;
import cn.linkmore.account.dao.master.NoticeReadMasterMapper;
import cn.linkmore.account.entity.Notice;
import cn.linkmore.account.entity.NoticeRead;
import cn.linkmore.account.request.ReqNotice;
import cn.linkmore.account.request.ReqPageNotice;
import cn.linkmore.account.response.ResNotice;
import cn.linkmore.account.response.ResPage;
import cn.linkmore.account.response.ResPageNotice;
import cn.linkmore.account.service.NoticeService;
import cn.linkmore.account.service.UserService;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.redis.RedisService;
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
		for (int i = 0; i < resPageNotic.size(); i++) {
			for (Long del : longs) {

				if (resPageNotic.get(i).getId() == del) {
					resPageNotic.get(i).setRead_status(NOTICE_OPER_READ);

				}
			}

		}
		ResPage<ResPageNotice> page = new ResPage<ResPageNotice>();
		int i = noticeClusterMapper.findNotReadCount( ru.getId());
		page.setRows(resPageNotic);
		page.setRecords((long) i);
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
	
	

}
