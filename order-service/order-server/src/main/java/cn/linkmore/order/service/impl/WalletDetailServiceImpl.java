package cn.linkmore.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.dao.cluster.WalletDetailClusterMapper;
import cn.linkmore.order.request.ReqWalletDetailExport;
import cn.linkmore.order.response.ResWalletDetail;
import cn.linkmore.order.response.ResWalletDetailExport;
import cn.linkmore.order.service.WalletDetailService;
import cn.linkmore.util.DomainUtil;
/**
 * 充值明细--实现
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Service
public class WalletDetailServiceImpl implements WalletDetailService {


	@Autowired
	private WalletDetailClusterMapper walletDetailMapper;
	/**
	 * 平台明显列表
	 */
	@Override
	public ViewPage getDetailList(ViewPageable pageable) {
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
		Integer count = this.walletDetailMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResWalletDetail> list = this.walletDetailMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	/*private List<WalletDetailResponseBean> getList(List<?> rows) {
		List<WalletDetailResponseBean> list = new ArrayList<>();
		for (Object obj: rows ) {
	        Map<?, ?> row = (Map<?, ?>) obj;
	        WalletDetailResponseBean bean = new WalletDetailResponseBean();
	        if(row.get("username")!=null){
	        	bean.setMobile(row.get("username").toString());
	        }
	        if(row.get("account_id")!=null){
	        	bean.setAccountId(Long.parseLong(row.get("account_id").toString()));
	        }
	        if(row.get("amount")!=null){
	        	bean.setAmount(Double.valueOf(row.get("amount").toString()));
	        }
	        if(row.get("account_amount")!=null){
	        	bean.setAccountAmount(Double.valueOf(row.get("account_amount").toString()));
	        }
	        if(row.get("source")!=null){
	        	int source = Integer.valueOf(row.get("source").toString());
	        	if(source == 1){
	        		bean.setSource("支付宝");
	        	}else if(source == 2){
	        		bean.setSource("微信");
	        	}else{
	        		bean.setSource("充值赠送");
	        	}
	        }
	        if(row.get("type")!=null){
	        	int type = Integer.valueOf(row.get("type").toString());
	        	if(type==1){
	        		bean.setType("消费");
	        		bean.setSource("------");
	        	}else if(type==2){
	        		bean.setType("充值");
	        	}
	        }
	        if(row.get("create_time")!=null){
	        	bean.setCreateTime((Date)(row.get("create_time")));
	        }
	        list.add(bean);
		}
		return list;
	}*/
	/**
	 * 导出根据时间查询
	 */
	@Override
	public List<ResWalletDetailExport> getListByTime(ReqWalletDetailExport bean) {
		List<ResWalletDetail> list = this.walletDetailMapper.getListByTime(bean);
		return getExcelList(list);
	}
	private List<ResWalletDetailExport> getExcelList(List<ResWalletDetail> rows) {
		List<ResWalletDetailExport> list = new ArrayList<>();
		for (ResWalletDetail row : rows) {
			ResWalletDetailExport bean = new ResWalletDetailExport();
	        if(row.getUsername() != null){
	        	bean.setMobile(row.getUsername().toString());
	        }
	        if(row.getAccountId() != null){
	        	bean.setAccountId(row.getAccountId());
	        }
	        if(row.getAmount() != null){
	        	bean.setAmount(row.getAmount().doubleValue());
	        }
	        if(row.getAccountAmount() != null){
	        	bean.setAccountAmount(Double.valueOf(row.getAccountAmount().toString()));
	        }
	        if(row.getSource() !=null){
	        	int source = Integer.valueOf(row.getSource().toString());
	        	if(source == 1){
	        		bean.setSource("支付宝");
	        	}else if(source == 2){
	        		bean.setSource("微信");
	        	}else{
	        		bean.setSource("充值赠送");
	        	}
	        }
	        if(row.getType() != null){
	        	int type = Integer.valueOf(row.getType().toString());
	        	if(type==1){
	        		bean.setType("消费");
	        		bean.setSource("------");
	        	}else if(type==2){
	        		bean.setType("充值");
	        	}
	        }
	        if(row.getCreateTime() != null){
	        	bean.setCreateTime((Date)(row.getCreateTime()));
	        }
	        list.add(bean);
		}
		return list;
	}
}
