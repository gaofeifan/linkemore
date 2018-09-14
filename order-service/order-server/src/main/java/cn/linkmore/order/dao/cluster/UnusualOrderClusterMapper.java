package cn.linkmore.order.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.order.entity.UnusualOrder;
import cn.linkmore.order.response.ResUnusualOrder;
@Mapper
public interface UnusualOrderClusterMapper {

	List<UnusualOrder> selectByCategory();

	List<ResUnusualOrder> findList(Map<String, Object> map);

}