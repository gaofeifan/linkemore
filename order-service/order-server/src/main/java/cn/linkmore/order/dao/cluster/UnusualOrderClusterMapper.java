package cn.linkmore.order.dao.cluster;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.order.entity.UnusualOrder;
@Mapper
public interface UnusualOrderClusterMapper {

	List<UnusualOrder> selectByCategory();
}