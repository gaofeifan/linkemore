package cn.linkmore.prefecture.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.Stall;

@Mapper
public interface StallClusterMapper {
	Stall selectByPrimaryKey(Long id);

	List<Stall> selectAll();

	List<Stall> getByStatus(int status);

	Stall getByLockSn(String lockSn);
	//CurrentOrderResponseBean getCurrentOrder(Long stallId);

	int getCountByPreId(Long preId);
	
	Stall getPreFreeStall(Long preId);
}