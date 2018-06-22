package cn.linkmore.account.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.UserBlacklist;
/**
 * - 权限模块mapper -- 写
 * @author   GFF
 * @Date     2018年6月20日
 * @Version  v2.0
 */
@Mapper
public interface UserBlacklistMasterMapper {
    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int deleteById(Long id);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int save(UserBlacklist record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int saveSelective(UserBlacklist record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(UserBlacklist record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(UserBlacklist record);

    /**
	 * @Description  解禁
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void enable(List<Long> list);

	/**
	 * @Description  清除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	int clear();

	/**
	 * @Description  批量新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	int batchSave();

	int deleteWhitelist();
}