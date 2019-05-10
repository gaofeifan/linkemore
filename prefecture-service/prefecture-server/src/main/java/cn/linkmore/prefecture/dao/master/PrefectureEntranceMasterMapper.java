package cn.linkmore.prefecture.dao.master;

import java.util.List;

import cn.linkmore.prefecture.entity.PrefectureEntrance;

public interface PrefectureEntranceMasterMapper {
	int delete(Long id);

	int save(PrefectureEntrance record);

	int update(PrefectureEntrance record);

	int deleteBatch(List<Long> ids);
}