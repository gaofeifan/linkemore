package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.UserInfo;

@Mapper
public interface UserInfoMasterMapper {  
	
	/**
	 * 保存
	 * @param record
	 * @return
	 */
    int save(UserInfo record);
 

    /**
     * 更新
     * @param record
     * @return
     */
    int update(UserInfo record);
}