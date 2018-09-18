package cn.linkmore.account.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.controller.staff.response.ResCustomerInfo;
import cn.linkmore.account.controller.staff.response.ResDict;
import cn.linkmore.account.service.CustomerInfoService;
import cn.linkmore.account.service.StaffCauseService;
import cn.linkmore.common.client.BaseDictClient;
import cn.linkmore.common.response.ResBaseDict;
import cn.linkmore.order.client.EntOrderClient;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.response.ResOrder;

/**
 * @author   GFF
 * @Date     2018年9月17日
 * @Version  v2.0
 */
@Service
public class StaffCauseServiceImpl implements StaffCauseService {

	@Resource
	private BaseDictClient baseDictClient;

	@Resource
	private OrderClient orderClient;

	@Resource
	private CustomerInfoService customerInfoService;
	
/*	@Override
	public ResCustomerInfo findCustoerData(Long oId) {
		List<String> list = new ArrayList<String>();
		list.add("customer-sex");
		list.add("customer-age");
		list.add("child-age");
		list.add("car-color");
		list.add("car-disp");
		list.add("park-cause");
		List<ResBaseDict> dbs = this.baseDictClient.findListByCodes(list);
		List<ResDict> sex = new ArrayList<ResDict>();
		List<ResDict> age = new ArrayList<ResDict>();
		List<ResDict> childAge = new ArrayList<ResDict>();
		List<ResDict> color = new ArrayList<ResDict>();
		List<ResDict> displacement = new ArrayList<ResDict>();
		List<ResDict> cause = new ArrayList<ResDict>();
		ResDict dic = null;
		for (ResBaseDict db : dbs) {
			dic = new ResDict();
			dic.setCode(db.getCode());
			dic.setId(db.getId());
			dic.setTitle(db.getName());
			switch (db.getCode()) {
			case "customer-sex":
				sex.add(dic);
				break;
			case "customer-age":
				age.add(dic);
				break;
			case "child-age":
				childAge.add(dic);
				break;
			case "car-color":
				color.add(dic);
				break;
			case "car-disp":
				displacement.add(dic);
				break;
			case "park-cause":
				cause.add(dic);
				break;
			}
		}
		ResCustomerInfo crb = new ResCustomerInfo();
		crb.setAge(age);
		crb.setCause(cause);
		crb.setChildAge(childAge);
		crb.setDisplacement(displacement);
		crb.setColor(color);
		crb.setSex(sex);

		// Added 选中内容
		boolean flag = true;
		ResOrder order = orderClient.findById(oId);
//		Orders orders = OrderClient.
		Long userId = orders.getUserId();
		CustomerInfo cus = customerInfoService.findByUserId(userId);
		if (cus != null) {
			flag =false;
			crb = this.getInitCheck(crb, cus);
		}
		crb.setFlag(flag);
		return crb;
	}

	private CustomerResponseBean getInitCheck(CustomerResponseBean crb, CustomerInfo cus) {
		Long age = cus.getAge();
		if (null != age) {
			List<DictBean> agelist = crb.getAge();
			for (DictBean dictBean : agelist) {
				if (dictBean.getId().longValue() == age) {
					dictBean.setCheck(true);
				}
			}
			crb.setAge(agelist);
		}
		Long stopCause = cus.getStopCause();
		if (null != stopCause) {
			List<DictBean> cause = crb.getCause();
			for (DictBean dictBean : cause) {
				if (dictBean.getId().longValue() == stopCause) {
					dictBean.setCheck(true);
					break;
				}
			}
			crb.setCause(cause);
		}
		Long carDis = cus.getCarDis();
		if (null != carDis) {
			List<DictBean> disList = crb.getDisplacement();
			for (DictBean dictBean : disList) {
				if (dictBean.getId().longValue() == carDis) {
					dictBean.setCheck(true);
					break;
				}
			}
			crb.setDisplacement(disList);
		}
		Long carColour = cus.getCarColour();
		if (null != carColour) {
			List<DictBean> colorList = crb.getColor();
			for (DictBean dictBean : colorList) {
				if (dictBean.getId().longValue() == carColour) {
					dictBean.setCheck(true);
					break;
				}
			}
			crb.setColor(colorList);
		}
		Long sex = cus.getSex();
		if (null != sex) {
			List<DictBean> sexList = crb.getSex();
			for (DictBean dictBean : sexList) {
				if (dictBean.getId().longValue() == sex) {
					dictBean.setCheck(true);
					break;
				}
			}
			crb.setSex(sexList);
		}
		String childAge = cus.getChildAge();
		if (StringUtils.isNotBlank(childAge)) {
			List<Long> ids = new ArrayList<>();
			String[] split = childAge.split("\\.");
			for (String string : split) {
				ids.add(Long.parseLong(string));
			}
			List<DictBean> childAgeList = crb.getChildAge();
			for (DictBean dictBean : childAgeList) {
				boolean contains = ids.contains(dictBean.getId());
				dictBean.setCheck(contains);
			}
			crb.setChildAge(childAgeList);
		}
		String brandName = "";
		if (cus.getBrandId() != null && cus.getModel() != null) {
			brandName = cus.getBrandName() + " " + cus.getModelName();
		}
		crb.setBrandName(brandName);
		return crb;
	}

	@Override
	public List<CauseReponseBean> findCode(String code) {
		Map<String, Object> param = new HashMap<>();
		param.put("code", code);
		return baseDictGroupMapper.findByCode(param);
	}
	*/
	
}
