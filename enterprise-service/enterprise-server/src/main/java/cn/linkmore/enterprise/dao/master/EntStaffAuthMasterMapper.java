package cn.linkmore.enterprise.dao.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntOperateAuth;
import cn.linkmore.enterprise.entity.EntStaffAuth;
/**
 * 企业用户权限---写
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntStaffAuthMasterMapper {
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
    int save(EntStaffAuth record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int saveSelective(EntStaffAuth record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    EntStaffAuth findById(Long id);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(EntStaffAuth record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(EntStaffAuth record);

	/**
	 * @Description  批量插入
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void saveBatch(List<EntStaffAuth> list);

	/**
	 * @Description  删除用户权限
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void deleteByStaffId(Long staffId);

}