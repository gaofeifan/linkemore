package cn.linkmore.account.dao.master;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.account.entity.ThirdUser;

@Mapper
public interface ThirdUserMasterMapper {
	
    int save(ThirdUser record);
    
	void update(Map<String, Object> param);

}