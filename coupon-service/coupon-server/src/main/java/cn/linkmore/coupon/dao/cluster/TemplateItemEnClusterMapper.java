package cn.linkmore.coupon.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.coupon.response.ResTemplateItem;

@Mapper
public interface TemplateItemEnClusterMapper {

    ResTemplateItem findById(Long id);

    Integer check(Map<String, Object> param);
    /**
     * 根据tempId查找items
     * @param templateId
     * @return
     */
    List<ResTemplateItem> findList(Long templateId);

	/**
	 * @Description 根据合同编号查询项
	 * @Author   GFF 
	 * @Date       2018年3月30日
	 * @Param    CouponTemplateItemEnMapper
	 * @Return   List<CouponTemplateItem>
	 */
	List<ResTemplateItem> selectBuDealNumber(String dealNumber);

	/**
	 * @Description  根据企业查询
	 * @Author   GFF 
	 * @Date       2018年4月3日
	 * @Param    CouponTemplateItemEnMapper
	 * @Return   List<CouponTemplateItem>
	 */
	List<ResTemplateItem> selectBuEnterpriseId(Long id);

	List<ResTemplateItem> selectByTemplateIds(List<String> ids);
}