package cn.linkmore.enterprise.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntUserPlate;
import cn.linkmore.enterprise.request.ReqEntUserPlate;
@Mapper
public interface EntUserPlateMasterMapper {
    int delete(Long id);

    int save(EntUserPlate record);

    int update(EntUserPlate record);

	void delete(List<Long> ids);

	int saveBatch(List<ReqEntUserPlate> plateList);
}