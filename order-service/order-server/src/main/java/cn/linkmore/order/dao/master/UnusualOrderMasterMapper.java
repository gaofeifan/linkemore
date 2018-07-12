package cn.linkmore.order.dao.master;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.order.entity.UnusualOrder;
@Mapper
public interface UnusualOrderMasterMapper {
   
    //根据不同分类进行删除
	void deleteByCategory(short category);
	
	void insertBatch(List<UnusualOrder> usualOrderList);

}