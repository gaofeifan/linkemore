package cn.linkmore.coupon.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.dao.cluster.RollbackClusterMapper;
import cn.linkmore.coupon.dao.cluster.SendUserClusterMapper;
import cn.linkmore.coupon.dao.cluster.TemplateEnClusterMapper;
import cn.linkmore.coupon.dao.master.RollbackMasterMapper;
import cn.linkmore.coupon.dao.master.SendUserMasterMapper;
import cn.linkmore.coupon.response.ResSendUser;
import cn.linkmore.coupon.response.ResSendUserList;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.coupon.service.SendUserService;
import cn.linkmore.util.DomainUtil;


@Service
public class SendUserServiceImpl implements SendUserService {
	
	@Autowired
	private SendUserClusterMapper sendUserClusterMapper;
	
	@Autowired
	private SendUserMasterMapper sendUserMasterMapper;
	
	@Autowired
	private TemplateEnClusterMapper templateEnClusterMapper;
	
	/*@Autowired
	private EnterpriseDealClusterMapper enterpriseDealClusterMapper;*/
	
	@Autowired
	private RollbackClusterMapper rollbackClusterMapper;
	
	@Autowired
	private RollbackMasterMapper rollbackMasterMapper;
	
	public ViewPage findPage(ViewPageable pageable) { 
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
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.sendUserClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResSendUserList> list = this.sendUserClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	@Transactional
	public void rollbackEnterpriseCoupon() {/*
		List<ResSendUser> list = this.sendUserClusterMapper.findExpireList();
		if(CollectionUtils.isNotEmpty(list)){
			Map<Long, List<ResSendUser>> templateMap = list.stream().collect(Collectors.groupingBy(ResSendUser::getTemplateId));
			List<Long> templateIds= new ArrayList<Long>();
			for(ResSendUser couponSendUser :list){
				couponSendUser.setRollbackFlag(1);
				templateIds.add(couponSendUser.getTemplateId());
			}
			List<ResTemplate> tempList = this.templateEnClusterMapper.findEnterpriseTemplate(templateIds);
			List<String> dealNumberList = new ArrayList<String>();
			for(ResTemplate template: tempList){
				dealNumberList.add(template.getEnterpriseDealNumber());
			}
			Map<String,EnterpriseDeal> map = new HashMap<String,EnterpriseDeal>();
			List<EnterpriseDeal> deals = enterpriseDealMapper.selectByDealNumbers(dealNumberList);
			for(EnterpriseDeal deal :deals){
				map.put(deal.getSerialNumber(), deal);
			}
			
			List<CouponRollback> rollbackList = new ArrayList<CouponRollback>();
			List<EnterpriseDeal> enterpriseDealList = new ArrayList<EnterpriseDeal>();
			CouponRollback rollback =null;
			
			for(CouponTemplate template :tempList){
				rollback = new CouponRollback();
				rollback.setRollbackDate(new Date());
				EnterpriseDeal enterpriseDeal = map.get(template.getEnterpriseDealNumber());
				rollback.setTemplateId(template.getId());
				rollback.setEnterpriseDealNumber(enterpriseDeal.getSerialNumber());
				rollback.setUsedDealPayAmount(new BigDecimal(enterpriseDeal.getUsedDealPayAmount().toString()).setScale(0, BigDecimal.ROUND_DOWN).doubleValue());
				rollback.setUserDealGiftAmount(new BigDecimal(enterpriseDeal.getUserDealGiftAmount().toString()).setScale(0, BigDecimal.ROUND_DOWN).doubleValue());
				int num = templateMap.get(template.getId()).size();
				if(template.getContractAmount()!=null ){
					Double contractAmount = template.getContractAmount() * num;
					if(template.getCouponExpires().equals(0)){
						enterpriseDeal.setUsedDealPayAmount(sub(enterpriseDeal.getUsedDealPayAmount(),contractAmount));
						rollback.setContractAmount(new BigDecimal(contractAmount.toString()).setScale(0, BigDecimal.ROUND_DOWN).doubleValue());
					}else if(template.getCouponExpires().equals(2)){
						enterpriseDeal.setUsedDealPayAmount(sub(enterpriseDeal.getUsedDealPayAmount(),contractAmount/2));
						rollback.setContractAmount(new BigDecimal(contractAmount/2).setScale(0, BigDecimal.ROUND_DOWN).doubleValue());
					}
				}
				if(template.getGivenAmount()!=null){
					Double givenAmount = template.getGivenAmount() * num;
					if(template.getMaturityAmount().equals(0)){
						enterpriseDeal.setUserDealGiftAmount(sub(enterpriseDeal.getUserDealGiftAmount(),givenAmount));
						rollback.setGivenAmount(new BigDecimal(givenAmount.toString()).setScale(0, BigDecimal.ROUND_DOWN).doubleValue());
					}else if(template.getMaturityAmount().equals(2)){
						enterpriseDeal.setUserDealGiftAmount(sub(enterpriseDeal.getUserDealGiftAmount(),givenAmount/2));
						rollback.setGivenAmount(new BigDecimal(givenAmount/2).setScale(0, BigDecimal.ROUND_DOWN).doubleValue());
					}
				}
				rollbackList.add(rollback);
				enterpriseDealList.add(enterpriseDeal);
			}
			this.enterpriseDealMasterMapper.updateBatch(enterpriseDealList);
			this.rollbackMasterMapper.insertBatch(rollbackList);
			this.sendUserMasterMapper.updateBatch(list);
		}
	*/}
	
	public static Double sub(Double v1, Double v2) {  
	   BigDecimal b1 = new BigDecimal(v1.toString());  
	   BigDecimal b2 = new BigDecimal(v2.toString()).setScale(0, BigDecimal.ROUND_DOWN); 
	   return new Double(b1.subtract(b2).doubleValue());  
	}  
	
}
