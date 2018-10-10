package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.linkmore.enterprise.controller.staff.request.CustomerRequestBean;
import cn.linkmore.enterprise.controller.staff.response.CustomerResponseBean;
import cn.linkmore.enterprise.controller.staff.response.DictBean;
import cn.linkmore.enterprise.dao.cluster.BaseDictGroupMapper;
import cn.linkmore.enterprise.dao.cluster.OrdersMapper;
import cn.linkmore.enterprise.dao.master.CustomerInfoMapper;
import cn.linkmore.enterprise.entity.CustomerInfo;
import cn.linkmore.enterprise.entity.Orders;
import cn.linkmore.enterprise.service.CusTomerInfoService;

@Service
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackForClassName = "RuntimeExcpeiton")
public class CusTomerInfoServiceImpl implements CusTomerInfoService {

	@Resource
	private CustomerInfoMapper customerInfoMapper;

	@Resource
	private OrdersMapper ordersMapper;

	@Resource
	private BaseDictGroupMapper baseDictGroupMapper;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public void save(CustomerRequestBean bean) {
		CustomerInfo cus = null;
		Orders orders = ordersMapper.find(bean.getOrderId());
		Long userId = orders.getUserId();
		cus = customerInfoMapper.selectByPrimaryKey(userId);
		Date createDate = new Date();
		CustomerResponseBean dict = this.findCustoerData();
		Object obj = redisTemplate.opsForValue().get("car_brand_list");
		if (cus == null) {
			cus = new CustomerInfo();
			cus.setId(userId);
			cus.setCreateTime(createDate);
			cus.setUpdateTime(createDate);
			cus.setAdminId(bean.getAdminId());
			if (null != bean.getAgeId()) {
				cus.setAge(bean.getAgeId());
				cus.setAgeName(this.findDidct(dict, 2, bean.getAgeId()));
			}

			if (null != bean.getBrandId() && null != bean.getModel()) {
				String brandName = "";
				String modelName = "";
				JSONArray jsonArray = JSON.parseArray(obj.toString());
				for (Object object : jsonArray) {
					JSONObject parObj = JSON.parseObject(object.toString());
					if (bean.getBrandId().intValue() == Integer.parseInt(parObj.get("id").toString())) {
						brandName = parObj.get("name").toString();
						JSONArray cArray = JSON.parseArray(parObj.get("childlist").toString());
						for (Object cobj : cArray) {
							JSONObject cparObj = JSON.parseObject(cobj.toString());
							if (bean.getBrandId().intValue() == Integer.parseInt(cparObj.get("parentid").toString())) {
								if(cparObj.get("carlist") != null){
									JSONArray carArray = JSON.parseArray(cparObj.get("carlist").toString());
									for (Object car : carArray) {
										JSONObject mobj = JSON.parseObject(car.toString());
										if (bean.getModel().intValue() == Integer.parseInt(mobj.get("id").toString())) {
											modelName = mobj.get("fullname").toString();
											break;
										}
									}
								}
							}
						}
						break;
					}
				}
				cus.setBrandId(bean.getBrandId());
				cus.setBrandName(brandName);
				cus.setModel(bean.getModel());
				cus.setModelName(modelName);
			}
			if (null != bean.getColorId()) {
				cus.setCarColour(bean.getColorId());
				cus.setCarColourName(this.findDidct(dict, 4, bean.getColorId()));
			}
			if (null != bean.getDisplacementId()) {
				cus.setCarDis(bean.getDisplacementId());
				cus.setCarDisName(this.findDidct(dict, 5, bean.getDisplacementId()));
			}

			if (null != bean.getSexId()) {
				cus.setSex(bean.getSexId());
				cus.setSexName(this.findDidct(dict, 1, bean.getSexId()));
			}
			if (bean.getCauseId() != null) {
				cus.setStopCause(bean.getCauseId());
				cus.setStopCauseName(this.findDidct(dict, 6, bean.getCauseId()));
			}
			
			if (StringUtils.isNotBlank(bean.getChildAgeIds()) && !"NotChanged".equals(bean.getChildAgeIds())) {
				cus.setChildAge(bean.getChildAgeIds());
				String ids = bean.getChildAgeIds();
				String[] split = ids.split("\\.");
				StringBuffer ageBuffer = new StringBuffer();
				for (String string : split) {
					long parseLong = Long.parseLong(string);
					ageBuffer.append(this.findDidct(dict, 3, parseLong));
					ageBuffer.append(",");
				}
				cus.setChildAgeName(ageBuffer.toString().substring(0, ageBuffer.toString().length() - 1));
				cus.setChildExist(0);
				cus.setChildNum(split.length);
			} else if ("NotChanged".equals(bean.getChildAgeIds())) {
				// 不做任何修改
			} else {
				cus.setChildAge("");
				cus.setChildAgeName("");
				cus.setChildNum(0);
				cus.setChildExist(1);
			}
			cus.setUserPlateNum(orders.getPlateNo());
			customerInfoMapper.insert(cus);
		} else {
			cus.setUpdateTime(createDate);
			cus.setAdminId(bean.getAdminId());
			if (null != bean.getAgeId()) {
				cus.setAge(bean.getAgeId());
				cus.setAgeName(this.findDidct(dict, 2, bean.getAgeId()));
			}
			if (null != bean.getBrandId() && null != bean.getModel()) {
				String brandName = "";
				String modelName = "";
				JSONArray jsonArray = JSON.parseArray(obj.toString());
				for (Object object : jsonArray) {
					JSONObject parObj = JSON.parseObject(object.toString());
					if (bean.getBrandId().intValue() == Integer.parseInt(parObj.get("id").toString())) {
						brandName = parObj.get("name").toString();
						JSONArray cArray = JSON.parseArray(parObj.get("childlist").toString());
						for (Object cobj : cArray) {
							JSONObject cparObj = JSON.parseObject(cobj.toString());
							int parseInt = Integer.parseInt(cparObj.get("parentid").toString());
							if (bean.getBrandId().intValue() == parseInt) {
								if(cparObj.get("carlist") != null  ){
									JSONArray carArray = JSON.parseArray(cparObj.get("carlist").toString());
									for (Object car : carArray) {
										JSONObject mobj = JSON.parseObject(car.toString());
										if (bean.getModel().intValue() == Integer.parseInt(mobj.get("id").toString())) {
											modelName = mobj.get("fullname").toString();
											break;
										}
									}
								}
							}
						}
						break;
					}
				}
				cus.setBrandId(bean.getBrandId());
				cus.setBrandName(brandName);
				cus.setModel(bean.getModel());
				cus.setModelName(modelName);
			}
			if (null != bean.getColorId()) {
				cus.setCarColour(bean.getColorId());
				cus.setCarColourName(this.findDidct(dict, 4, bean.getColorId()));
			}
			if (null != bean.getDisplacementId()) {
				cus.setCarDis(bean.getDisplacementId());
				cus.setCarDisName(this.findDidct(dict, 5, bean.getDisplacementId()));
			}
			if (null != bean.getSexId()) {
				cus.setSex(bean.getSexId());
				cus.setSexName(this.findDidct(dict, 1, bean.getSexId()));
			}
			if (bean.getCauseId() != null) {
				cus.setStopCause(bean.getCauseId());
				cus.setStopCauseName(this.findDidct(dict, 6, bean.getCauseId()));
			}
			
			if (StringUtils.isNotBlank(bean.getChildAgeIds()) && !"NotChanged".equals(bean.getChildAgeIds())) {
				cus.setChildAge(bean.getChildAgeIds());
				String ids = bean.getChildAgeIds();
				String[] split = ids.split("\\.");
				StringBuffer ageBuffer = new StringBuffer();
				for (String string : split) {
					long parseLong = Long.parseLong(string);
					ageBuffer.append(this.findDidct(dict, 3, parseLong));
					ageBuffer.append(",");
				}
				cus.setChildAgeName(ageBuffer.toString().substring(0, ageBuffer.toString().length() - 1));
				cus.setChildExist(0);
				cus.setChildNum(split.length);
			} else if ("NotChanged".equals(bean.getChildAgeIds())) {
				// 不做任何修改
			} else {
				cus.setChildAge("");
				cus.setChildAgeName("");
				cus.setChildNum(0);
				cus.setChildExist(1);
			}
			cus.setUserPlateNum(orders.getPlateNo());
			customerInfoMapper.updateByPrimaryKey(cus);
		}

	}

	private String findDidct(CustomerResponseBean bean, int type, Long id) {
		String res = "";
		List<DictBean> dictList = null;
		switch (type) {
		case 1:
			dictList = bean.getSex();
			break;
		case 2:
			dictList = bean.getAge();
			break;
		case 3:
			dictList = bean.getChildAge();
			break;
		case 4:
			dictList = bean.getColor();
			break;
		case 5:
			dictList = bean.getDisplacement();
			break;
		case 6:
			dictList = bean.getCause();
			break;
		}
		for (DictBean dictBean : dictList) {
			if (dictBean.getId().longValue() == id.longValue()) {
				res = dictBean.getTitle();
				break;
			}
		}
		return res;
	}

	private CustomerResponseBean findCustoerData() {
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
		return crb;
	}

}
