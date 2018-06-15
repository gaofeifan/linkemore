package cn.linkmore.coupon.utils;

import java.util.HashSet;
import java.util.List;
import cn.linkmore.coupon.entity.TemplateItem;
import cn.linkmore.coupon.response.ResTemplateItem;
import cn.linkmore.util.ObjectUtils;

public class CouponUtils {
	
	/**
	 * @Description  拼接优惠劵详情
	 * @Author   GFF 
	 * @Date       2018年4月17日
	 * @Param    CouponUtils
	 * @Return   String
	 */
	public static final String joinCouponDetails(List<ResTemplateItem> items ,Long tempId){
		StringBuilder sb = new StringBuilder();
		int lj = 0;
		int mj = 0;
		int zk = 0;
		for(ResTemplateItem item :items){
			if(item.getTemplateId().equals(tempId)){
				if (Integer.valueOf(0).equals(item.getType())) {
					lj +=item.getQuantity();
				}else if(Integer.valueOf(1).equals(item.getType())){
					mj +=item.getQuantity();
				}else if(Integer.valueOf(2).equals(item.getType())){
					zk +=item.getQuantity();
				}
			}
		}
		if(lj != 0){
			sb.append("立减券:" + lj+"张 ");
		}
		if(mj != 0){
			sb.append("满减券:"+ mj+"张 ");
		}
		if(zk != 0){
			sb.append("折扣券:"+ zk+"张 ");
		}
		return sb.toString();
	}
	/**
	 * @Description  拼接优惠劵详情(有换行)
	 * @Author   GFF 
	 * @Date       2018年4月17日
	 * @Param    CouponUtils
	 * @Return   String
	 */
	public static final String joinCouponDetailsDiv(List<ResTemplateItem> items ,Long tempId){
		StringBuilder sb = new StringBuilder();
		for(ResTemplateItem item :items){
			if (Integer.valueOf(0).equals(item.getType())) {
				sb.append("<div>" + item.getFaceAmount().doubleValue() + "元立减券" + item.getQuantity() + "张有效期"
						+ item.getValidDay() + "天    " + "<div><hr>");
			} else if (Integer.valueOf(1).equals(item.getType())) {
				sb.append("<div>" + "满" + item.getConditionAmount().doubleValue() + "元减"
						+ item.getFaceAmount().doubleValue() + "元" + item.getQuantity() + "张有效期" + item.getValidDay()
						+ "天" + "<div><hr>");
			} else if (Integer.valueOf(2).equals(item.getType())) {
				sb.append("<div>" + item.getDiscount() / 10.0 + "折优惠券" + item.getQuantity() + "张有效期"
						+ item.getValidDay() + "天    " + "<div><hr>");
			}
		}
		return sb.toString();
	}
	
	/**
	 * @Description  查询优惠劵类型   
	 * @Author   GFF 
	 * @Date       2018年4月17日
	 * @Param    CouponUtils
	 * @Return   Integer  3 为混合  其他为固定优惠劵
	 */
	public static final Integer getCouponType(List<?> list,Object obj){
		List<String> couponTypes = ObjectUtils.findFieldVlaue(list, "type",new String[]{"templateId"},new Object[]{obj});
		HashSet<String> set = new HashSet<>(couponTypes);
		if(set.size() == 0){
			return null;
		}else if(set.size() == 1){
			return Integer.parseInt(set.iterator().next());
		}else{
			return 3;
		}
		
	}

}
