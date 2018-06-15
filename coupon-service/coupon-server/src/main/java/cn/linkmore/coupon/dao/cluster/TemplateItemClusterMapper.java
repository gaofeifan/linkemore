package cn.linkmore.coupon.dao.cluster;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.coupon.response.ResTemplateItem;
/**
 * 优惠券套餐项
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface TemplateItemClusterMapper {

	List<ResTemplateItem> findItemList(List<Long> ids);

	List<ResTemplateItem> findList(Long id);
}