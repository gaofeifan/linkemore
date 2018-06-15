package cn.linkmore.coupon.dao.master;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.coupon.entity.TemplateItem;
import cn.linkmore.coupon.response.ResTemplateItem;

@Mapper
public interface TemplateItemEnMasterMapper {
	
    int delete(Long id);

    int save(TemplateItem record);

    int update(TemplateItem record);
    
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
	void insertBatch(List<ResTemplateItem> items);

    
}