package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.enterprise.controller.staff.response.CauseReponseBean;
import cn.linkmore.enterprise.controller.staff.response.CustomerResponseBean;
import cn.linkmore.enterprise.controller.staff.response.DictBean;
import cn.linkmore.enterprise.dao.cluster.BaseDictGroupMapper;
import cn.linkmore.enterprise.dao.cluster.OrdersMapper;
import cn.linkmore.enterprise.dao.master.CustomerInfoMapper;
import cn.linkmore.enterprise.entity.CustomerInfo;
import cn.linkmore.enterprise.entity.Orders;
import cn.linkmore.enterprise.service.CauseService;



/**
 * @author zhangxurui
 * @version v1.0.0
 * @description - 操作原因备注
 * @date 2017/11/8 上午9:57
 */
@Service
public class CauseServiceImpl implements CauseService {

	@Resource
	private BaseDictGroupMapper baseDictGroupMapper;

	@Resource
	private OrdersMapper ordersMapper;

	@Resource
	private CustomerInfoMapper customerInfoMapper;

	@Override
	public List<CauseReponseBean> findRemark(String code) {
		Map<String, Object> param = new HashMap<>();
		param.put("code", code);
		return baseDictGroupMapper.findByCode(param);
	}

	@Override
	public CustomerResponseBean findCustoerData(Long oId) {
		List<String> list = new ArrayList<String>();
		list.add("customer-sex");
		list.add("customer-age");
		list.add("child-age");
		list.add("car-color");
		list.add("car-disp");
		list.add("park-cause");
		List<DictBean> dbs = this.baseDictGroupMapper.findCustomerBean(list);
		List<DictBean> sex = new ArrayList<DictBean>();
		List<DictBean> age = new ArrayList<DictBean>();
		List<DictBean> childAge = new ArrayList<DictBean>();
		List<DictBean> color = new ArrayList<DictBean>();
		List<DictBean> displacement = new ArrayList<DictBean>();
		List<DictBean> cause = new ArrayList<DictBean>();
		for (DictBean db : dbs) {
			switch (db.getCode()) {
			case "customer-sex":
				sex.add(db);
				break;
			case "customer-age":
				age.add(db);
				break;
			case "child-age":
				childAge.add(db);
				break;
			case "car-color":
				color.add(db);
				break;
			case "car-disp":
				displacement.add(db);
				break;
			case "park-cause":
				cause.add(db);
				break;
			}
		}
		CustomerResponseBean crb = new CustomerResponseBean();
		crb.setAge(age);
		crb.setCause(cause);
		crb.setChildAge(childAge);
		crb.setDisplacement(displacement);
		crb.setColor(color);
		crb.setSex(sex);

		// Added 选中内容
		boolean flag = true;
		Orders orders = ordersMapper.find(oId);
		Long userId = orders.getUserId();
		CustomerInfo cus = customerInfoMapper.selectByPrimaryKey(userId);
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
	
	
	

}
