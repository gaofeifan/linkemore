package cn.linkmore.coupon.dao.master;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.request.ReqTemplate;

@Mapper
public interface TemplateEnMasterMapper {
	
    int deleteById(Long id);

    int save(ReqTemplate record);

    int update(ReqTemplate record);
    
	int startOrStop(Map<String, Object> param);
}