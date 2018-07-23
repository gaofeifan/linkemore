package cn.linkmore.enterprise.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntAuthPre;
/**
 * 权限专区---写
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntAuthPreMasterMapper {
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
    int save(EntAuthPre record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int saveSelective(EntAuthPre record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(EntAuthPre record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(EntAuthPre record);

	/**
	 * @Description  批量插入
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void saveBatch(List<EntAuthPre> pres);

	/**
	 * @Description  根据权限id删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void deleteByAuthId(Long id);
}