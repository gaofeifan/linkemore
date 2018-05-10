package cn.linkmore.account.dao.master;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.User;
@Mapper
public interface UserMasterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int updateByPrimaryKey(User record);
    
    int updateLoginTime(Map<String,Object> param);
    
    int updateNickname(Map<String,Object> param);
    
    int updateSex(Map<String,Object> param);
    	
    int updateMobile(Map<String,Object> param);
}