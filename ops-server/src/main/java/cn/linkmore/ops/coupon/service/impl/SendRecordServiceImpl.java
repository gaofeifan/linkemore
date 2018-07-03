package cn.linkmore.ops.coupon.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.account.client.UserClient;
import cn.linkmore.bean.common.Constants;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.SendRecordClient;
import cn.linkmore.coupon.request.ReqSendRecord;
import cn.linkmore.coupon.response.ResSendRecord;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.coupon.response.ResTemplateItem;
import cn.linkmore.ops.coupon.service.SendRecordService;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

@Service
public class SendRecordServiceImpl implements SendRecordService {
	@Autowired
	private SendRecordClient sendRecordClient;
	
	public ViewPage findPage(ViewPageable pageable) {
		return this.sendRecordClient.list(pageable);
	}

	public int save(ReqSendRecord record) {
		return this.sendRecordClient.save(record);
	}

	@Override
	public int saveBusiness(ReqSendRecord record) {
		return this.sendRecordClient.saveBusiness(record);
	}

	@Override
	public void timingForSend() {
		this.sendRecordClient.timingForSend();
		/*
		List<CouponSendRecord> recordList = this.couponSendRecordMapper.findTaskList();
		for(CouponSendRecord couponSendRecord :recordList){
			if(couponSendRecord.getTaskTime().compareTo(new Date()) <= 0 ){
				String phoneJson = this.redisTemplate.opsForValue().get(RedisKey.COUPON_SEND_RECORD_MOBILE + couponSendRecord.getId());
				String[] list = phoneJson.split(",");
				Set<String> set = new HashSet<String>();
				for (String phone : list) {
					if (phone != "") {
						set.add(phone);
					}
				}
				CouponTemplate temp = this.couponTemplateMapper.find(couponSendRecord.getTemplateId());
				List<CouponTemplateItem> items = couponTemplateItemMapper.findList(couponSendRecord.getTemplateId());
				List<CouponSendUser> sendUserList = new ArrayList<CouponSendUser>();
				for (String phone : set) {
					User user = getUser(phone);
					CouponSendUser couponSendUser = new CouponSendUser();
					couponSendUser.setCreateTime(new Date());
					couponSendUser.setUserId(user.getId());
					couponSendUser.setRecordId(couponSendRecord.getId());
					couponSendUser.setUsername(user.getUsername());
					couponSendUser.setTemplateId(couponSendRecord.getTemplateId());
					sendUserList.add(couponSendUser);
				}
				this.couponSendUserMapper.insertBatch(sendUserList);
				
				List<Coupon> couponList = new ArrayList<Coupon>();
				Coupon coupon = null;
				for(CouponSendUser couponSendUser :sendUserList){
					
					for (CouponTemplateItem item : items) {
						//停车券里配置多少张发送多少张
						for (int i = 0; i < item.getQuantity(); i++) {
							coupon = new Coupon();
							coupon.setUserId(couponSendUser.getUserId());
							coupon.setConditionId(couponSendRecord.getConditionId());
							coupon.setTemplateId(couponSendRecord.getTemplateId());
							coupon.setRecordId(couponSendRecord.getId());
							coupon.setItemId(item.getId());
							coupon.setType(item.getType());
							coupon.setFaceAmount(item.getFaceAmount());
							coupon.setDiscount(item.getDiscount());
							coupon.setConditionAmount(item.getConditionAmount());
							coupon.setValidTime(DateUtils.getDate(new Date(), 0, 0, item.getValidDay(), 0, 0, 0));
							coupon.setStatus((short) 0);
							coupon.setCreateTime(new Date());
							coupon.setSendUserId(couponSendUser.getId());
							couponList.add(coupon);
						}
					}
				}
				couponMapper.insertBatch(couponList);
				for(CouponSendUser couponSendUser :sendUserList){
					Map<String, String> param = new HashMap<String, String>();
					param.put("money", temp.getUnitAmount().toString());
					param.put("enterprise","凌猫停车");
					smsService.send(couponSendUser.getUsername(), 8,  SmsTemplateId.share_coupon_code, param);
				}
				
				//已结束
				couponSendRecord.setStatus(1);
				couponSendRecord.setSendTime(new Date());
				this.couponSendRecordMapper.updateByPrimaryKey(couponSendRecord);
				redisTemplate.delete(RedisKey.USER_GROUP_IDS+couponSendRecord.getId());
			}
		}
	*/}
}
