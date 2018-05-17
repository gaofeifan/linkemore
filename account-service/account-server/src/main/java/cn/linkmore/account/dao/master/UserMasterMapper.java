package cn.linkmore.account.dao.master;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.User;
@Mapper
public interface UserMasterMapper {
    int deleteById(Long id);

    int insert(User record);

    int insertSelective(User record);

    int updateByIdSelective(User record);

    int updateById(User record);

	void updateByColumn(Map<String, Object> param);

	void updateLoginTime(Map<String, Object> param);

	void updateMobile(Map<String, Object> param);
}