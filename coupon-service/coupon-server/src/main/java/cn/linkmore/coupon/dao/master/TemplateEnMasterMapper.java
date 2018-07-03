package cn.linkmore.coupon.dao.master;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.Template;
import cn.linkmore.coupon.request.ReqTemplate;

@Mapper
public interface TemplateEnMasterMapper {
	
    int deleteById(Long id);

    int save(ReqTemplate record);

    int updateById(Template temp);

    int updateByIdSelective(Template temp);
    
	int startOrStop(Map<String, Object> param);

	void updateDeleteStatus(Map<String, Object> map);

	void save(Template temp);
}