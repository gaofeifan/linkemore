package cn.linkmore.enterprise.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntAuthStall;
/**
 * 授权车位 -- 写
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntAuthStallMasterMapper {
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
    int save(EntAuthStall record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int saveSelective(EntAuthStall record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(EntAuthStall record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(EntAuthStall record);

	/**
	 * @Description  批量插入
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void saveBatch(List<EntAuthStall> stalls);

	/**
	 * @Description  根据权限删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void deleteByAuthId(Long id);
}