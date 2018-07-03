package cn.linkmore.coupon.dao.master;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.TemplateItem;
import cn.linkmore.coupon.request.ReqTemplateItem;
import cn.linkmore.coupon.response.ResTemplateItem;

@Mapper
public interface TemplateItemEnMasterMapper {
	
    int delete(Long id);

    int save(TemplateItem record);

	/**
	 * @Description 根据模板id删除
	 * @Author GFF 
	 * @Date 2018年3月28日
	 * @Return int
	 */
	int deleteByTempId(Long tempId);
	/**
	 * 批量插入
	 * @param items
	 */
	void insertBatch(List<TemplateItem> items);

	void updateById(TemplateItem item);

	/**
	 * @Description  更新删除状态
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateDeletaStatus(Map<String, Object> map);


    
}