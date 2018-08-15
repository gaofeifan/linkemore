package cn.linkmore.coupon.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.Template;
import cn.linkmore.coupon.response.ResTemplate;

@Mapper
public interface TemplateEnClusterMapper {
	
    ResTemplate findById(Long id);
    
    Integer count(Map<String, Object> param);

	Integer check(Map<String, Object> param);

	List<ResTemplate> findPage(Map<String, Object> param);
	
	/**
	 * @Description  根据企业查询优惠劵
	 * @Author   GFF 
	 * @Date       2018年3月28日
	 * @Return   List<ResTemplate>
	 */
	List<ResTemplate> findByEnterpriseId(Long entId);

	/**
	 * @Description 根据合同查询
	 * @Author   GFF 
	 * @Date       2018年3月28日
	 * @Param    CouponTemplateEnMapper
	 * @Return   CouponTemplate
	 */
	Template findByEnterpriseNumber(String number);
	/**
	 * 查找企业优惠券模板
	 * @param templateIds
	 * @return
	 */
	List<ResTemplate> findEnterpriseTemplate(List<Long> templateIds);

	/**
	 * @Description  查询详情
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResTemplate find(Long id);
	/**
	 * 根据企业id查询优惠券列表
	 * @param entId
	 * @return
	 */
	List<ResTemplate> findEntTemplateList(Long entId);

}