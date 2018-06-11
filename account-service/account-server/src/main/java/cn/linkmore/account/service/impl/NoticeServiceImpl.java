package cn.linkmore.account.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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

	@Override
	public ResPage page(ReqPageNotice bean) {
		int start = bean.getPage() * bean.getPageSize();
		Map<String, Object> map = new HashMap<>();
		map.put("start", start);
		map.put("size", bean.getPageSize());
		map.put("uid", bean.getUserId());
		List<ResPageNotice> resPageNotic = noticeClusterMapper.findPageNotice(map);
		List<Long> longs = noticeClusterMapper.findNoticeReadDel(bean.getUserId());
		for (int i = 0; i < resPageNotic.size(); i++) {

			for (Long del : longs) {

				if (resPageNotic.get(i).getId() == del) {
					resPageNotic.get(i).setRead_status(NOTICE_OPER_READ);

				}
			}

		}
		ResPage<ResPageNotice> page = new ResPage<ResPageNotice>();
		int i = noticeClusterMapper.findNotReadCount(bean.getUserId());
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

}
