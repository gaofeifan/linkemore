package cn.linkmore.prefecture.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.StallLock;
@Mapper
public interface StallLockMasterMapper {
    int delete(Long id);

    int save(StallLock record);

    int update(StallLock record);

	int batchSave(List<StallLock> locks);

	int updateBind(StallLock lock);

	int unBind(List<Long> ids);
	
	void insertAndGetId(StallLock lock);
}